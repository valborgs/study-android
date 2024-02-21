package kr.co.lion.android45_localization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android45_localization.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            textView2.setText(R.string.greeting)
            imageView2.setImageResource(R.drawable.flag1)
        }
    }
}