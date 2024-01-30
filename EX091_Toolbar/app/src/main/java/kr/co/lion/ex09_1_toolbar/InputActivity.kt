package kr.co.lion.ex09_1_toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex09_1_toolbar.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    lateinit var activityinputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityinputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityinputBinding.root)

        initData()
        initView()
        setEvent()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){
        activityinputBinding.apply {
            inputToolbar.apply {
                inflateMenu(R.menu.input_menu)
                // 좌측 상단의 홈 버튼 아이콘을 설정한다.
                setNavigationIcon(R.drawable.arrow_back_24px)
                // 좌측 상단의 홈 버튼 아이콘을 누르면 동작하는 리스너
                setNavigationOnClickListener {
                    // 현재 Activitiy를 종료시킨다.
                    finish()
                }
            }
        }
    }
    // 이벤트 설정
    fun setEvent(){
        activityinputBinding.apply {

            inputToolbar.apply {
                setOnMenuItemClickListener{
                    when(it.itemId){
                        R.id.addInfo -> {
                            // 누락된 칸이 있는 경우 포커스 주기
                            if(inputName.text.toString() == ""){
                                inputName.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            if(inputGrade.text.toString() == ""){
                                inputGrade.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            if(inputKor.text.toString() == ""){
                                inputKor.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            if(inputEng.text.toString() == ""){
                                inputEng.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            if(inputMath.text.toString() == "") {
                                inputMath.requestFocus()
                                return@setOnMenuItemClickListener false
                            }

                            // 입력한 정보를 객체에 담는다.
                            val name = inputName.text.toString()
                            val grade = inputGrade.text.toString().toInt()
                            val kor = inputKor.text.toString().toInt()
                            val eng = inputEng.text.toString().toInt()
                            val math = inputMath.text.toString().toInt()

                            val info1 = InfoClass(name, grade, kor, eng, math)

                            // 데이터를 담을 Intent를 생성한다.
                            val resultIntent = Intent()
                            // 객체를 Intent에 저장할 때 writeToParcel 메서가 호출된다.
                            resultIntent.putExtra("obj", info1)

                            // 결과를 셋팅한다.
                            setResult(RESULT_OK, resultIntent)

                            // 현재 Activity를 종료한다.
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }
}