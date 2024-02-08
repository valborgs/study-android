package kr.co.lion.androidproject1

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isGone
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.androidproject1.databinding.ActivityEditAnimalInfoBinding
import kotlin.math.round

class EditAnimalInfoActivity : AppCompatActivity() {

    lateinit var activityEditAnimalInfoBinding: ActivityEditAnimalInfoBinding

    lateinit var animal: Animal



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityEditAnimalInfoBinding = ActivityEditAnimalInfoBinding.inflate(layoutInflater)
        setContentView(activityEditAnimalInfoBinding.root)

        setToolbar()
        initView()
        setEvent()
    }

    fun initView(){

        // 동물 객체 가져오기
        if(VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            animal = intent.getParcelableExtra("animalData",Animal::class.java)!!
        }else{
            animal = intent.getParcelableExtra<Animal>("animalData")!!
        }

        activityEditAnimalInfoBinding.apply {
            hideInput()
            // 이름
            textInputEditName.setText(animal.name)
            // 나이
            textInputEditAge.setText(animal.age.toString())
            // 동물 타입 별로 고유 값 넣어주기
            when(animal.type){
                "사자" ->{
                    containerEditType1.isGone = false
                    // 사자로 형변환
                    val lion = animal as Lion
                    // 털의 개수
                    textInputEditHair.setText(lion.hair.toString())
                    // 성별
                    when(lion.gender){
                        "암컷" -> {
                            toggleButtonEditGender1.performClick()
                        }
                        "수컷" -> {
                            toggleButtonEditGender2.performClick()
                        }
                    }
                }
                "호랑이" ->{
                    containerEditType2.isGone = false
                    // 호랑이로 형변환
                    val tiger = animal as Tiger
                    // 줄무늬 개수
                    textInputEditStripe.setText(tiger.stripe.toString())
                    // 몸무게
                    sliderEditWeight.value = tiger.weight.toFloat()

                }
                "기린" ->{
                    containerEditType3.isGone = false
                    // 기린으로 형변환
                    val giraff = animal as Giraff
                    // 목의 길이
                    textInputEditNeck.setText(giraff.neck.toString())
                    // 달리는 속도
                    textInputEditSpeed.setText(giraff.speed.toString())
                }
            }
        }
    }

    fun hideInput(){
        activityEditAnimalInfoBinding.apply {
            containerEditType1.isGone = true
            containerEditType2.isGone = true
            containerEditType3.isGone = true
        }
    }

    fun setEvent(){
        activityEditAnimalInfoBinding.apply {
            sliderEditWeight.addOnChangeListener { slider, value, fromUser ->
                textView.text = "몸무게 ${round(value*10) /10}kg"
            }
        }
    }

    // 툴바 셋팅
    fun setToolbar(){
        activityEditAnimalInfoBinding.apply {
            toolbarEdit.apply {
                // 타이틀
                title = "동물 정보 수정"
                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                inflateMenu(R.menu.edit_menu)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_editConfirm -> {
                            val editIntent = Intent(this@EditAnimalInfoActivity, ShowAnimalInfoActivity::class.java)

                            // 등록 전 체크
                            if(checkInput()){
                                return@setOnMenuItemClickListener false
                            }

                            // 동물 객체 생성
                            val newAnimal:Animal
                            val name = textInputEditName.text.toString()
                            val age = textInputEditAge.text.toString().toInt()
                            when(animal.type){
                                "사자" -> {
                                    val hair = textInputEditHair.text.toString().toInt()
                                    val gender = when(toggleGroupEditGender.checkedButtonId){
                                        R.id.toggleButtonEditGender1 -> "암컷"
                                        R.id.toggleButtonEditGender2 -> "수컷"
                                        else -> ""
                                    }
                                    newAnimal = Lion("사자",name,age,hair,gender)
                                    editIntent.putExtra("editAnimalData",newAnimal)
                                }
                                "호랑이" -> {
                                    val stripe = textInputEditStripe.text.toString().toInt()
                                    val weight = sliderEditWeight.value.toDouble()
                                    newAnimal = Tiger("호랑이",name,age,stripe,weight)
                                    editIntent.putExtra("editAnimalData",newAnimal)
                                }
                                "기린" -> {
                                    val neck = textInputEditNeck.text.toString().toInt()
                                    val speed = textInputEditSpeed.text.toString().toInt()
                                    newAnimal = Giraff("기린",name,age,neck,speed)
                                    editIntent.putExtra("editAnimalData",newAnimal)
                                }
                            }

                            setResult(RESULT_OK,editIntent)
                            finish()
                        }
                    }
                    true
                }

            }
        }
    }

    fun checkInput():Boolean{
        var chk = false
        activityEditAnimalInfoBinding.apply {
            // 이름 누락
            if(checkString(textInputEditName)){
                showDialog("이름")
                chk=true
                return@apply
            }
            // 나이 누락
            if(checkString(textInputEditAge)){
                showDialog("나이")
                chk=true
                return@apply
            }
            when(animal.type){
                "사자" -> {
                    // 털의 개수 누락
                    if(checkString(textInputEditHair)){
                        showDialog("털의 개수")
                        chk=true
                        return@apply
                    }
                    // 성별 누락
                    if(toggleGroupEditGender.checkedButtonId==-1){
                        showDialog("성별")
                        chk=true
                        return@apply
                    }
                }
                "호랑이" -> {
                    // 줄무늬 개수 누락
                    if(checkString(textInputEditStripe)){
                        showDialog("줄무늬 개수")
                        chk=true
                        return@apply
                    }
                    // 몸무게 누락
                    if(sliderEditWeight.value<=50){
                        showDialog("몸무게")
                        chk=true
                        return@apply
                    }
                }
                "기린" -> {
                    // 목의 길이 누락
                    if(checkString(textInputEditNeck)){
                        showDialog("목의 길이")
                        chk=true
                        return@apply
                    }
                    // 달리는 속도 누락
                    if(checkString(textInputEditSpeed)){
                        showDialog("달리는 속도")
                        chk=true
                        return@apply
                    }
                }
            }
        }
        return chk
    }

    fun checkString(textView: TextView):Boolean{
        return textView.text.toString().trim() == ""
    }

    fun showDialog(text:String){
        val builder = MaterialAlertDialogBuilder(this@EditAnimalInfoActivity).apply {
            setTitle("입력 누락")
            setMessage("${text}를 입력해주세요")
            setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            }
        }
        builder.show()
    }

}