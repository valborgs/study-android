package kr.co.lion.androidproject1test

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kr.co.lion.androidproject1test.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)

        setToolbar()
        initView()
        setEvent()
    }

    // 툴바 설정
    fun setToolbar(){

        activityInputBinding.apply {
            toolbarInput.apply {
                // 타이틀
                title = "동물 등록"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.menu_input)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        // Done
                        R.id.menu_item_input_done -> {
                            // 유효성 검사 메서드를 호출한다.
                            checkInput()
                        }
                    }
                    true
                }
            }
        }
    }

    // View들의 초기 상태를 설정한다.
    fun initView() {
        activityInputBinding.apply {
            // 버튼 그룹에서 사자를 선택한다.
            buttonGroupInputType.check(R.id.buttonInputType1)
            // 입력요소를 관리하는 layout들 중 사자는 보이게 하고
            // 나머지들은 보이지 않게 한다.
            containerInputType1.isVisible = true
            containerInputType2.isVisible = false
            containerInputType3.isVisible = false
            // 사자의 성별은 암컷으로 선택한다.
            buttonGroupInputGender.check(R.id.buttonInputGender1)

            // 이름 입력칸에 포커스를 준다.
            Util.showSoftInput(activityInputBinding.textFieldInputName,this@InputActivity)
        }
    }

    // View들의 이벤트 설정
    fun setEvent(){
        activityInputBinding.apply {
            // 동물 타입 버튼 그룹
            buttonGroupInputType.addOnButtonCheckedListener { group, checkedId, isChecked ->

                // 전부 다 안보이는 상태로 변경한다.
                containerInputType1.isVisible = false
                containerInputType2.isVisible = false
                containerInputType3.isVisible = false

                // 현재 체크되어 있는 버튼에 따라 보여지는 부분을 달리한다.
                when(buttonGroupInputType.checkedButtonId){
                    // 사자
                    R.id.buttonInputType1 -> {
                        containerInputType1.isVisible = true
                        // 입력 요소 초기화
                        textFieldInputHairCount.setText("")
                        buttonGroupInputGender.check(R.id.buttonInputGender1)
                    }
                    // 호랑이
                    R.id.buttonInputType2 -> {
                        containerInputType2.isVisible = true
                        // 입력 요소 초기화
                        textFieldInputLineCount.setText("")
                        sliderInputWeight.value = sliderInputWeight.valueFrom
                    }
                    // 기린
                    R.id.buttonInputType3 -> {
                        containerInputType3.isVisible = true
                        // 입력 요소 초기화
                        textFieldInputNeckLength.setText("")
                        textFieldInputRunSpeed.setText("")
                    }
                }

                // 이름 입력칸에 포커스를 준다.
                Util.showSoftInput(activityInputBinding.textFieldInputName,this@InputActivity)

                
            }
            // 호랑이 몸무게 슬라이더
            // 두 번째 : 현재 설정된 값
            // 세 번째 : 코드가 아닌 사용자에 의해서 값이 변경될 경우 true를 전달해줌
            sliderInputWeight.addOnChangeListener { slider, value, fromUser ->
                // 텍스트 뷰에 출력한다.
                textViewInputWeight.text = "몸무게 : ${value.toInt()}kg"

            }
        }
    }

    // 입력 유효성 검사
    fun checkInput(){

        activityInputBinding.apply {
            // 이름
            val name = textFieldInputName.text.toString()
            if(name.trim().isEmpty()){
                Util.showInfoDialog(this@InputActivity,"이름 입력 오류","이름을 입력해주세요"){ dialogInterface, i ->
                    Util.showSoftInput(textFieldInputName, this@InputActivity)
                }
                return
            }

            // 나이
            val age = textFieldInputAge.text.toString()
            if(age.trim().isEmpty()){
                Util.showInfoDialog(this@InputActivity, "나이 입력 오류", "나이를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                    Util.showSoftInput(textFieldInputAge,this@InputActivity)
                }
                return
            }

            // 동물 종류별로 분기한다.
            when(buttonGroupInputType.checkedButtonId){
                // 사자
                R.id.buttonInputType1 -> {
                    // 털의 개수
                    val hairCount = textFieldInputHairCount.text.toString()
                    if(hairCount.trim().isEmpty()){
                        Util.showInfoDialog(this@InputActivity, "털의 개수 입력 오류", "털의 개수를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                            Util.showSoftInput(textFieldInputHairCount,this@InputActivity)
                        }
                        return
                    }
                }
                // 호랑이
                R.id.buttonInputType2 -> {
                    // 줄무늬 개수
                    val lineCount = textFieldInputLineCount.text.toString()
                    if(lineCount.trim().isEmpty()){
                        Util.showInfoDialog(this@InputActivity,"줄무늬 개수 입력 오류", "줄무늬 개수를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                            Util.showSoftInput(textFieldInputLineCount,this@InputActivity)
                        }
                        return
                    }
                }
                // 기린
                R.id.buttonInputType3 -> {
                    // 목의 길이
                    val neckLength = textFieldInputNeckLength.text.toString()
                    if(neckLength.trim().isEmpty()){
                        Util.showInfoDialog(this@InputActivity,"목의 길이 입력 오류", "목의 길이를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                            Util.showSoftInput(textFieldInputNeckLength,this@InputActivity)
                        }
                        return
                    }
                    // 달리는 속도
                    val runSpeed = textFieldInputRunSpeed.text.toString()
                    if(runSpeed.trim().isEmpty()){
                        Util.showInfoDialog(this@InputActivity,"달리는 속도 입력 오류", "달리는 속도를 입력해주세요"){ dialogInterface: DialogInterface, i: Int ->
                            Util.showSoftInput(textFieldInputRunSpeed,this@InputActivity)
                        }
                        return
                    }
                }
            }

            // 저장처리
            addAnimalData()
            // 액티비티를 종료한다.
            finish()
        }
    }

    // 저장처리
    fun addAnimalData(){
        activityInputBinding.apply {
            // 사용자가 선택한 동물의 종류에 따라 분기한다.
            when(buttonGroupInputType.checkedButtonId){
                // 사자
                R.id.buttonInputType1 -> {
                    val lion = Lion()
                    lion.type = AnimalType.ANIMAL_TYPE_LION
                    lion.name = textFieldInputName.text.toString()
                    lion.age = textFieldInputAge.text.toString().toInt()
                    lion.hairCount = textFieldInputHairCount.text.toString().toInt()
                    lion.gender = when(buttonGroupInputGender.checkedButtonId){
                        R.id.buttonInputGender1 -> LION_GENDER.LION_GENDER1
                        R.id.buttonInputGender2 -> LION_GENDER.LION_GENDER2
                        else -> LION_GENDER.LION_GENDER1
                    }
                    Util.animalList.add(lion)
                }
                // 호랑이
                R.id.buttonInputType2 -> {
                    val tiger = Tiger()
                    tiger.type = AnimalType.ANIMAL_TYPE_TIGER
                    tiger.name = textFieldInputName.text.toString()
                    tiger.age = textFieldInputAge.text.toString().toInt()
                    tiger.lineCount = textFieldInputLineCount.text.toString().toInt()
                    tiger.weight = sliderInputWeight.value.toInt()
                    Util.animalList.add(tiger)
                }
                // 기린
                R.id.buttonInputType3 -> {
                    val giraffe = Giraffe()
                    giraffe.type = AnimalType.ANIMAL_TYPE_GIRAFFE
                    giraffe.name = textFieldInputName.text.toString()
                    giraffe.age = textFieldInputAge.text.toString().toInt()
                    giraffe.neckLength = textFieldInputNeckLength.text.toString().toInt()
                    giraffe.runSpeed = textFieldInputRunSpeed.text.toString().toInt()
                    Util.animalList.add(giraffe)
                }
            }
        }
    }
}