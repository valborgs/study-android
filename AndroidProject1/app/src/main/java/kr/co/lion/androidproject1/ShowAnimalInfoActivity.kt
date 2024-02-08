package kr.co.lion.androidproject1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.IntentCompat.getParcelableExtra
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.androidproject1.databinding.ActivityShowAnimalInfoBinding

class ShowAnimalInfoActivity : AppCompatActivity() {

    lateinit var activityShowAnimalInfoBinding: ActivityShowAnimalInfoBinding

    // 동물 객체
    lateinit var animal: Animal

    // 동물 정보 수정 런처
    lateinit var activityEditAnimalInfoLauncher: ActivityResultLauncher<Intent>
    var changed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowAnimalInfoBinding = ActivityShowAnimalInfoBinding.inflate(layoutInflater)
        setContentView(activityShowAnimalInfoBinding.root)

        setLauncher()
        setToolbar()
        setView()
    }

    // 런처 셋팅
    fun setLauncher(){

        // 동물 정보 수정
        val contract1 = ActivityResultContracts.StartActivityForResult()
        activityEditAnimalInfoLauncher = registerForActivityResult(contract1){
            if(it.resultCode == RESULT_CANCELED) return@registerForActivityResult
            if(it.data != null){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    animal = it.data!!.getParcelableExtra("editAnimalData",Animal::class.java)!!
                }else{
                    animal = it.data!!.getParcelableExtra<Animal>("editAnimalData")!!
                }
                // 정보 출력
                activityShowAnimalInfoBinding.textViewShowInfo.text = animal.getInfo()

                // 수정 메시지
                val snackBar = Snackbar.make(activityShowAnimalInfoBinding.root,"수정 완료", Snackbar.LENGTH_SHORT)
                snackBar.show()

                changed = true
            }
        }
    }

    // 툴바 셋팅
    fun setToolbar(){
        activityShowAnimalInfoBinding.apply {
            toolbarAnimalInfo.apply {
                // 타이틀
                title = "동물 정보"
                inflateMenu(R.menu.show_menu)

                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    when(changed){
                        true -> {
                            // 정보 수정을 했을 경우 수정한 객체도 전달
                            val editIntent = Intent(this@ShowAnimalInfoActivity,MainActivity::class.java)
                            editIntent.putExtra("newAnimal",animal)
                            editIntent.putExtra("animalPosition",intent.getIntExtra("animalPosition",-1))
                            setResult(RESULT_OK,editIntent)
                            finish()
                        }
                        false -> {
                            // 정보 수정 안한 경우 그냥 뒤로가기
                            setResult(RESULT_CANCELED)
                            finish()
                        }
                    }
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 수정 메뉴 클릭
                        R.id.menu_item_edit -> {
                            val editIntent = Intent(this@ShowAnimalInfoActivity,EditAnimalInfoActivity::class.java)
                            editIntent.putExtra("animalData",animal)
                            activityEditAnimalInfoLauncher.launch(editIntent)
                        }
                        // 삭제 메뉴 클릭
                        R.id.menu_item_delete -> {
                            val deleteIntent = Intent(this@ShowAnimalInfoActivity,MainActivity::class.java)
                            deleteIntent.putExtra("animalPosition",intent.getIntExtra("animalPosition",-1))
                            setResult(RESULT_FIRST_USER,deleteIntent)
                            finish()
                        }
                    }
                    true
                }

            }
        }
    }

    // 뷰 설정
    fun setView(){
        activityShowAnimalInfoBinding.apply {
            // TextView
            textViewShowInfo.apply {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    animal = intent.getParcelableExtra("animalData",Animal::class.java)!!
                }else{
                    animal = intent.getParcelableExtra<Animal>("animalData")!!
                }
                // 정보 출력
                text = animal.getInfo()
            }
        }
    }
}