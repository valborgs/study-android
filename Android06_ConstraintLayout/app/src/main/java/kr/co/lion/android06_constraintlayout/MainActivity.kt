package kr.co.lion.android06_constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Constraint Layout
// Constraint Layout에 배치되는 뷰들은 부모 혹은 다른 뷰들과의 관계를 나타내는
// 제약 조건을 설정하여 뷰들을 배치한다.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}