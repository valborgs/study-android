package kr.co.lion.android02_testproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

// step1) 화면 부터 구성해준다.
// onCreate 메서드에 있는 코드 중에 setContentView 메서드의 매개변수에
// 설정되어 있는 것을 확인하여 res/layout 폴더에 있는 xml 파일을 열어준다.

// step2) Activity로 돌아와서 필요한 코드들을 작성한다.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // id 속성이 textView인 화면 요소 객체를 가져온다.
        val testView1 = findViewById<TextView>(R.id.textView)
        // id 속성이 button인 화면 요소 객체를 가져온다.
        val testView2 = findViewById<Button>(R.id.button)

        // 화면 요소 중에 이벤트 처리를 설정한다.
        // 어떠한 사건이 벌어지면 안드로이드 os가 호출될 메서드를 구현하여 설정한다.
        testView2.setOnClickListener(object : OnClickListener{
            // 버튼을 클릭하면 호출되는 메서드
            override fun onClick(p0: View?) {
                // 버튼이 눌러지면 동작할 코드를 작성한다.
                testView1.text = "안녕하세요~~"
            }
        })

    }
}