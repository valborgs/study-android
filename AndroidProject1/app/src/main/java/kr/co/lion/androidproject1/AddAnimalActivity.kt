package kr.co.lion.androidproject1

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.androidproject1.databinding.ActivityAddAnimalBinding
import kotlin.math.round

class AddAnimalActivity : AppCompatActivity() {

    lateinit var activityAddAnimalBinding: ActivityAddAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddAnimalBinding = ActivityAddAnimalBinding.inflate(layoutInflater)
        setContentView(activityAddAnimalBinding.root)

        setToolbar()
        initView()
        setEvent()
    }

    fun initView(){
        activityAddAnimalBinding.apply {
            hideInput()
            sliderWeight.addOnChangeListener { slider, value, fromUser -> 
                textView.text = "몸무게 ${round(value*10)/10}kg"
            }
        }
    }

    fun hideInput(){
        activityAddAnimalBinding.apply {
            containerType1.isGone = true
            containerType2.isGone = true
            containerType3.isGone = true
        }
    }

    fun setEvent(){
        activityAddAnimalBinding.apply {
            toggleGroupAnimalType.addOnButtonCheckedListener { group, checkedId, isChecked ->
                when(checkedId){
                    R.id.toggleButtonLion -> {
                        if(isChecked){
                            containerType1.isGone = false
                            containerType2.isGone = true
                            containerType3.isGone = true
                        }
                    }
                    R.id.toggleButtonTiger -> {
                        if(isChecked){
                            containerType1.isGone = true
                            containerType2.isGone = false
                            containerType3.isGone = true
                        }
                    }
                    R.id.toggleButtonGiraff -> {
                        if(isChecked){
                            containerType1.isGone = true
                            containerType2.isGone = true
                            containerType3.isGone = false
                        }

                    }
                }
            }
        }
    }

    // 툴바 셋팅
    fun setToolbar(){
        activityAddAnimalBinding.apply {
            toolbarAdd.apply {
                // 타이틀
                title = "동물 등록"
                inflateMenu(R.menu.add_menu)

                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_addConfirm -> {
                            // AddAnimalActivity 실행
                            val addIntent = Intent(this@AddAnimalActivity, MainActivity::class.java)
                            
                            // 등록 전 체크
                            if(checkInput()){
                                return@setOnMenuItemClickListener false
                            }

                            // 동물 객체 생성
                            val animal:Animal
                            val name = textInputName.text.toString()
                            val age = textInputAge.text.toString().toInt()
                            when(toggleGroupAnimalType.checkedButtonId){
                                R.id.toggleButtonLion -> {
                                    val hair = textInputHair.text.toString().toInt()
                                    val gender = when(toggleGroupGender.checkedButtonId){
                                        R.id.toggleButtonGender1 -> "암컷"
                                        R.id.toggleButtonGender2 -> "수컷"
                                        else -> ""
                                    }
                                    animal = Lion("사자",name,age,hair,gender)
                                    addIntent.putExtra("animalData",animal)
                                }
                                R.id.toggleButtonTiger -> {
                                    val stripe = textInputStripe.text.toString().toInt()
                                    val weight = sliderWeight.value.toDouble()
                                    animal = Tiger("호랑이",name,age,stripe,weight)
                                    addIntent.putExtra("animalData",animal)
                                }
                                R.id.toggleButtonGiraff -> {
                                    val neck = textInputNeck.text.toString().toInt()
                                    val speed = textInputSpeed.text.toString().toInt()
                                    animal = Giraff("기린",name,age,neck,speed)
                                    addIntent.putExtra("animalData",animal)
                                }
                            }

                            setResult(RESULT_OK,addIntent)
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
        activityAddAnimalBinding.apply {
            // 동물 종류 누락
            if(toggleGroupAnimalType.checkedButtonId==-1){
                showDialog("동물 종류")
                chk=true
                return@apply
            }
            // 이름 누락
            if(checkString(textInputName)){
                showDialog("이름")
                chk=true
                return@apply
            }
            // 나이 누락
            if(checkString(textInputAge)){
                showDialog("나이")
                chk=true
                return@apply
            }
            when(toggleGroupAnimalType.checkedButtonId){
                R.id.toggleButtonLion -> {
                    // 털의 개수 누락
                    if(checkString(textInputHair)){
                        showDialog("털의 개수")
                        chk=true
                        return@apply
                    }
                    // 성별 누락
                    if(toggleGroupGender.checkedButtonId==-1){
                        showDialog("성별")
                        chk=true
                        return@apply
                    }
                }
                R.id.toggleButtonTiger -> {
                    // 줄무늬 개수 누락
                    if(checkString(textInputStripe)){
                        showDialog("줄무늬 개수")
                        chk=true
                        return@apply
                    }
                    // 몸무게 누락
                    if(sliderWeight.value<=50){
                        showDialog("몸무게")
                        chk=true
                        return@apply
                    }
                }
                R.id.toggleButtonGiraff -> {
                    // 목의 길이 누락
                    if(checkString(textInputNeck)){
                        showDialog("목의 길이")
                        chk=true
                        return@apply
                    }
                    // 달리는 속도 누락
                    if(checkString(textInputAge)){
                        showDialog("달리는 속도")
                        chk=true
                        return@apply
                    }
                }
            }
        }
        return chk
    }

    fun checkString(textView:TextView):Boolean{
        return textView.text.toString().trim() == ""
    }

    fun showDialog(text:String){
        val builder = MaterialAlertDialogBuilder(this@AddAnimalActivity).apply {
            setTitle("입력 누락")
            setMessage("${text}를 입력해주세요")
            setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            }
        }
        builder.show()
    }
}