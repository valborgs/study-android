package kr.co.lion.android14_scrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android14_scrollview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            // ScrollView의 Scroll 이벤트
            // 두 번째 : 스크롤 된 X 좌표
            // 세 번째 : 스크롤 된 Y 좌표
            // 네 번째 : 스크롤 되기 전 X 좌표
            // 다섯 번째 : 스크롤 되기 전 Y 좌표
            scroll1.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> 
                textView.text = "X : ${oldScrollY} -> ${scrollY}"
            }
            // HorizontalScrollView의 Scroll 이벤트
            scroll2.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                textView2.text = "X : ${oldScrollX} -> ${scrollX}"
            }

            button.setOnClickListener {
                // 현재의 좌표를 가져온다.
                 textView.text = "현재의 X 좌표 : ${scroll2.scrollX}"
                 textView2.text = "현재의 Y 좌표 : ${scroll1.scrollY}"
            }

            button2.setOnClickListener {
                // 지정된 위치로 이동한다.
                scroll1.scrollTo(0,300)
                scroll2.scrollTo(300,0)
                // 지정한 만큼 이동한다.
                scroll1.scrollBy(0,100)
                scroll2.scrollBy(100,0)
                // 지정된 위치로 이동한다. (애니메이션)
                scroll1.smoothScrollTo(0,300)
                scroll2.smoothScrollTo(300,0)
                // 지정한 만큼 이동한다. (애니메이션)
                scroll1.smoothScrollBy(0,100)
                scroll2.smoothScrollBy(100,0)
            }
        }
    }
}