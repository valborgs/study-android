package kr.co.lion.ex10

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.ex10.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)

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

        activityInputBinding.apply {
            toolbarInput.apply {
                title = "학생 정보 입력"
                setTitleTextColor(Color.WHITE)

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
                            // 다이얼로그
                            val builder = MaterialAlertDialogBuilder(this@InputActivity)
                            builder.setTitle("입력 누락")
                            // 이름 누락
                            if(inputName.text.toString() == ""){
                                builder.setMessage("이름을 입력해주세요.")
                                builder.show()
                                inputName.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            // 학년 누락
                            if(inputGrade.text.toString() == ""){
                                builder.setMessage("이름을 입력해주세요.")
                                builder.show()
                                inputGrade.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            // 국어 점수 누락
                            if(inputKor.text.toString() == ""){
                                builder.setMessage("국어 점수를 입력해주세요.")
                                builder.show()
                                inputKor.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            // 영어 점수 누락
                            if(inputEng.text.toString() == ""){
                                builder.setMessage("영어 점수를 입력해주세요.")
                                builder.show()
                                inputEng.requestFocus()
                                return@setOnMenuItemClickListener false
                            }
                            // 수학 점수 누락
                            if(inputMath.text.toString() == ""){
                                builder.setMessage("수학 점수를 입력해주세요.")
                                builder.show()
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
    // View 초기 셋팅
    fun initView(){

    }
    // 이벤트 설정
    fun setEvent(){

    }
}