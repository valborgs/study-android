package kr.co.lion.ex12_sqlitedatabase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex12_sqlitedatabase1.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {

    lateinit var activityShowInfoBinding: ActivityShowInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowInfoBinding = ActivityShowInfoBinding.inflate(layoutInflater)
        setContentView(activityShowInfoBinding.root)

        setToolbar()
        setView()
    }

    // 툴바 설정
    fun setToolbar(){
        activityShowInfoBinding.apply {
            toolbarShowInfo.apply {
                // 타이틀
                title = "학생 정보 보기"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
            }
        }
    }

    // 뷰 설정
    fun setView(){
        activityShowInfoBinding.apply {
            // TextView
            textViewShowInfoReport.apply {

                // 학생 번호를 추출한다.
                val idx = intent.getIntExtra("idx",0)
                // 데이터베이스에서 읽어온다.
                val studentModel = StudentDao.selectOneStudent(this@ShowInfoActivity,idx)

                text = "이름 : ${studentModel.name}\n"
                append("학년 : ${studentModel.grade}\n")
                append("\n")
                append("국어 점수 : ${studentModel.kor}점\n")
                append("영어 점수 : ${studentModel.eng}점\n")
                append("수학 점수 : ${studentModel.math}점\n")
                append("\n")

                val total = studentModel.kor + studentModel.eng + studentModel.math
                val avg = total / 3

                append("총점 : ${total}점\n")
                append("평균 : ${avg}점")
            }
        }
    }
}