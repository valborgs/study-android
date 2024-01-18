package kr.co.lion.testproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView6)
        val buttonView = findViewById<Button>(R.id.button2)
        var switch = false
        val switchLight = findViewById<ImageView>(R.id.imageView)
        val switchLight2 = findViewById<ImageView>(R.id.imageView2)

        buttonView.setOnClickListener(object: OnClickListener{
            override fun onClick(p0: View?) {
                if(switch){
                    switch = false
                    switchLight.setImageResource()
                    println()
                }else{
                    switch = true
                    switchLight.setImageResource()
                }

            }
        })
    }
}