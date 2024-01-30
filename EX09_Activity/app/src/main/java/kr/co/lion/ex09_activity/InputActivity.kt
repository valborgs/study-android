package kr.co.lion.ex09_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex09_activity.databinding.ActivityInputBinding
import kr.co.lion.ex09_activity.databinding.ActivityMainBinding

class InputActivity : AppCompatActivity() {

    lateinit var activityinputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityinputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityinputBinding.root)

        initData()
        initToolbar()
        initView()
        setEvent()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // 툴바 설정
    fun initToolbar(){
        activityinputBinding.apply {
            toolbarInput.apply {
                title = "학생 정보 입력"

                // 뒤로가기
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                inflateMenu(R.menu.input_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.input_menu_item1 -> {
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
    // View 초기 셋팅
    fun initView(){

    }
    // 이벤트 설정
    fun setEvent(){
        activityinputBinding.apply {

        }
    }
}