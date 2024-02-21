package kr.co.lion.android43_imageresource

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android43_imageresource.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // 이미지 뷰에 설정한 애니메이션 이미지를 추출한다.
                val animationDrawable = imageView2.drawable as AnimationDrawable
                // 애니메이션 가동
                animationDrawable.start()
            }

            button2.setOnClickListener {
                // 이미지 뷰에 설정한 애니메이션 이미지를 추출한다.
                val animationDrawable = imageView2.drawable as AnimationDrawable
                // 애니메이션 정지
                animationDrawable.stop()
            }
        }
    }
}