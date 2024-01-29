package kr.co.lion.ex09_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex09_activity.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    
    lateinit var activityReportBinding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityReportBinding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(activityReportBinding.root)

        initData()
        initView()
        setEvent()
    }

    // 초기 데이터 셋팅
    fun initData(){

    }
    // View 초기 셋팅
    fun initView(){
        activityReportBinding.apply { 
            textViewReport.apply {

                // Activity가 실행되었을 때 사용한 Intent로부터 데이터들을 추출한다.
                val chk1 = intent?.getBooleanExtra("chk1",false)

                val korTotal = intent?.getIntExtra("korTotal",0)
                val engTotal = intent?.getIntExtra("engTotal",0)
                val mathTotal = intent?.getIntExtra("mathTotal",0)

                val korAvg = intent?.getIntExtra("korAvg",0)
                val engAvg = intent?.getIntExtra("engAvg",0)
                val mathAvg = intent?.getIntExtra("mathAvg",0)

                val allTotal = intent?.getIntExtra("allTotal",0)
                val allAvg = intent?.getIntExtra("allAvg",0)

                if(chk1 == false){
                    text = "등록된 학생 정보가 없습니다."
                }else{
                    text = "국어 총점 : ${korTotal}점\n"
                    append("국어 평균 : ${korAvg}점\n")
                    append("영어 총점 : ${engTotal}점\n")
                    append("영어 평균 : ${engAvg}점\n")
                    append("수학 총점 : ${mathTotal}점\n")
                    append("수학 평균 : ${mathAvg}점\n")
                    append("\n")
                    append("전체 총점 : ${allTotal}점\n")
                    append("전체 평균 : ${allAvg}점\n")
                }
            }
        }
    }
    // 이벤트 설정
    fun setEvent(){
        activityReportBinding.apply {
            // 버튼 이벤트
            buttonOk.setOnClickListener {
                // 현재 Activity를 종료한다.
                finish()
            }
        }
    }
}