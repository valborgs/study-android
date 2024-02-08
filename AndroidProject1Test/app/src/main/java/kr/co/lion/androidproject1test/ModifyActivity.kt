package kr.co.lion.androidproject1test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kr.co.lion.androidproject1test.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var activityModifyBinding: ActivityModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(activityModifyBinding.root)

        setToolbar()
        initView()
    }

    // 툴바 설정
    fun setToolbar(){

        activityModifyBinding.apply {
            toolbarModify.apply {
                // 타이틀
                title = "동물 정보 수정"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.menu_modify)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_modify_done -> {
                            modifyData()
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }

    // View 설정
    fun initView(){

        activityModifyBinding.apply {
            // 각 동물 별 입력 요소를 숨긴다.
            containerModifyType1.isVisible = false
            containerModifyType2.isVisible = false
            containerModifyType3.isVisible = false

            // 순서값을 추출한다.
            val position = intent.getIntExtra("position",0)
            // position 번째 객체를 추출한다.
            val animal = Util.animalList[position]

            // 공통
            textFieldModifyName.setText(animal.name)
            textFieldModifyAge.setText(animal.age.toString())
            // 사자
            if(animal is Lion){
                // 입력 요소들을 보이게 한다.
                containerModifyType1.isVisible = true
                // 값을 넣어준다.
                textFieldModifyHairCount.setText(animal.hairCount.toString())
                when(animal.gender){
                    LION_GENDER.LION_GENDER1 -> buttonGroupModifyGender.check(R.id.buttonModifyGender1)
                    LION_GENDER.LION_GENDER2 -> buttonGroupModifyGender.check(R.id.buttonModifyGender2)
                }
            }
            // 호랑이
            if(animal is Tiger){
                // 입력 요소들을 보이게 한다.
                containerModifyType2.isVisible = true
                // 값을 넣어준다.
                textFieldModifyLineCount.setText(animal.lineCount.toString())
                sliderModifyWeight.value = animal.weight.toFloat()
            }
            // 기린
            if(animal is Giraffe){
                // 입력 요소들을 보이게 한다.
                containerModifyType3.isVisible = true
                // 값을 넣어준다.
                textFieldModifyNeckLength.setText(animal.neckLength.toString())
                textFieldModifyRunSpeed.setText(animal.runSpeed.toString())
            }
        }
    }

    // 수정 처리
    fun modifyData(){
        // 위치 값을 가져온다.
        val position = intent.getIntExtra("position",0)
        // position번째 객체를 가져온다.
        val animal = Util.animalList[position]

        activityModifyBinding.apply {
            // 공통
            animal.name = textFieldModifyName.text.toString()
            animal.age = textFieldModifyAge.text.toString().toInt()
            // 클래스 타입별로 분기
            // 사자
            if(animal is Lion){
                animal.hairCount = textFieldModifyHairCount.text.toString().toInt()
                animal.gender = when(buttonGroupModifyGender.checkedButtonId){
                    R.id.buttonModifyGender1 -> LION_GENDER.LION_GENDER1
                    R.id.buttonModifyGender2 -> LION_GENDER.LION_GENDER2
                    else -> LION_GENDER.LION_GENDER1
                }
            }
            // 호랑이
            else if(animal is Tiger){
                animal.lineCount = textFieldModifyLineCount.text.toString().toInt()
                animal.weight = sliderModifyWeight.value.toInt()
            }
            // 기린
            else if(animal is Giraffe){
                animal.neckLength = textFieldModifyNeckLength.text.toString().toInt()
                animal.runSpeed = textFieldModifyRunSpeed.text.toString().toInt()
            }
        }
    }
}