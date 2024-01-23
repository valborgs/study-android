package kr.co.lion.android19_progressslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android19_progressslider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // progressIndicator의 값을 설정한다.
                // progressBar.progress += 10
                progressBar.setProgress(progressBar.progress + 10,true)
            }

            button2.setOnClickListener {
                textView.text = "progress value : ${progressBar.progress}"
            }

            button3.setOnClickListener {
                slider.value = 80.0f
            }

            button4.setOnClickListener {
                textView.text = "slider value : ${slider.value}"
            }
        }
    }
}