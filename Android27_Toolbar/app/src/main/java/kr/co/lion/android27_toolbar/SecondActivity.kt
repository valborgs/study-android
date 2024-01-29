package kr.co.lion.android27_toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android27_toolbar.databinding.ActivityMainBinding
import kr.co.lion.android27_toolbar.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var activitySecondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)

        activitySecondBinding.apply {
            toolbarSecond.apply {
                title = "SecondActivity"

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
}