package kr.co.lion.assignment02

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.assignment02.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    lateinit var activityInfoBinding: ActivityInfoBinding

    // 메모 수정 런처
    lateinit var activityModifyLauncher: ActivityResultLauncher<Intent>

    // 수정 여부
    var isChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInfoBinding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(activityInfoBinding.root)

        setLauncher()
        setToolbar()
        initView()
        setEvent()
    }

    fun setLauncher(){
        // 메모 수정 런처
        val contact1 = ActivityResultContracts.StartActivityForResult()
        activityModifyLauncher = registerForActivityResult(contact1){
            when(it.resultCode){
                RESULT_OK -> {
                    initView()
                    isChanged = true
                    // 수정 완료 메시지
                    Snackbar.make(activityInfoBinding.root,"수정 완료",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setToolbar(){
        activityInfoBinding.apply {
            toolbarInfo.apply {
                // 타이틀
                setTitle("메모 보기")
                setTitleTextColor(Color.WHITE)
                // 레이아웃
                inflateMenu(R.menu.menu_info)
                // 뒤로가기 버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    when(isChanged){
                        true -> {
                            // 수정 한 경우
                            setResult(RESULT_OK)
                        }
                        false -> {
                            // 수정 안한 경우
                            setResult(RESULT_CANCELED)
                        }
                    }
                    finish()
                }
                // 메뉴 버튼
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 수정 버튼
                        R.id.menu_item_info_modify -> {
                            val modifyIntent = Intent(this@InfoActivity,ModifyActivity::class.java)
                            modifyIntent.putExtra("position",intent.getIntExtra("position",-1))
                            activityModifyLauncher.launch(modifyIntent)
                        }
                        // 삭제 버튼
                        R.id.menu_item_info_delete -> {
                            Util.memoList.removeAt(intent.getIntExtra("position",-1))
                            setResult(RESULT_FIRST_USER)
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }

    fun initView(){
        activityInfoBinding.apply {
            val memo = Util.memoList[intent.getIntExtra("position",-1)]
            if(memo != null){
                textInputInfoTitle.setText(memo.title)
                textInputInfoDate.setText(memo.date.toString())
                textInputInfoContent.setText(memo.content)
            }

        }
    }

    fun setEvent(){

    }
}