package kr.co.lion.android21_materialcardview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android21_materialcardview.databinding.ActivityMainBinding

// CardView
// 화면에 배치되는 뷰들을 카드라는 것으로 묶어서 표현할 수 있다.
// style
// Outlined : 기본
// Filled
// Elevated
class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
}