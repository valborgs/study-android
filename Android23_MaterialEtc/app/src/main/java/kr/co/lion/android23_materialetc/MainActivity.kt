package kr.co.lion.android23_materialetc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android23_materialetc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {

            chip1.setOnClickListener {
                textView.text = "chip1을 눌렀습니다."
            }
        }
    }
}