package kr.co.lion.android07_textview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android07_textview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // TextView 사용

        // activityMainBinding.textView.text = "새로운 문자열"

        activityMainBinding.apply {
            // TextView의 프로퍼티와 메서드 사용
            textView.apply{
                // 문자열을 설정한다.
                text = "새로운 문자열\n"
                // 문자열을 추가한다.
                // \n : 아래로 내린다는 의미의 글자
                append("문자열2\n")
                append("문자열3\n")
            }
        }

    }
}