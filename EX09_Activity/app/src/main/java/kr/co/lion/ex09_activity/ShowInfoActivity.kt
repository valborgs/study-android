package kr.co.lion.ex09_activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex09_activity.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {

    lateinit var activityShowInfoBinding: ActivityShowInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowInfoBinding = ActivityShowInfoBinding.inflate(layoutInflater)
        setContentView(activityShowInfoBinding.root)

        initData()
        initView()
        setEvent()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){
        activityShowInfoBinding.apply {
            textView2.apply {

                // intent로 부터 객체를 추출한다
                val info1 = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    intent?.getParcelableExtra("obj", InfoClass::class.java)
                }else{
                    intent?.getParcelableExtra<InfoClass>("obj")
                }

                text = "이름 : ${info1?.name}\n"
                append("학년 : ${info1?.grade}학년\n")
                append("\n")
                append("국어 점수 : ${info1?.kor}점\n")
                append("영어 점수 : ${info1?.eng}점\n")
                append("수학 점수 : ${info1?.math}점\n")
                append("\n")

                val total = info1!!.kor + info1!!.eng + info1!!.math
                append("총점 : ${total}점\n")
                val avg = total / 3
                append("평균 : ${avg}점")
            }
        }
    }
    // 이벤트 설정
    fun setEvent(){
        activityShowInfoBinding.apply {
            // 버튼을 눌렀을 때
            button2.setOnClickListener {
                // 현재 Activity를 종료한다.
                finish()
            }
        }
    }
}