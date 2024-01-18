package kr.co.lion.ex05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex05.databinding.ActivityMainBinding

// 숫자 두 개를 입력받는다.
// 더하기 버튼을 누르면 더한 결과를 출력한다.
// 빼기 버튼을 누르면 뺀 결과를 출력한다.
// 곱하기 버튼을 누르면 곱한 결과를 출력한다.
// 나누기 버튼을 누르면 나눈 결과를 출력한다.

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            // 더하기 버튼
            button1.apply {
                setOnClickListener {
                    val num1:Int = editTextText1.text.toString().toInt()
                    val num2:Int = editTextText2.text.toString().toInt()
                    val result = (num1 + num2).toString()
                    textView3.text = "총합 : $result"
                }
            }
            // 빼기 버튼
            button2.apply {
                setOnClickListener {
                    val num1:Int = editTextText1.text.toString().toInt()
                    val num2:Int = editTextText2.text.toString().toInt()
                    val result = (num1 - num2).toString()
                    textView3.text = "총합 : $result"
                }
            }
            // 곱하기 버튼
            button3.apply {
                setOnClickListener {
                    val num1:Int = editTextText1.text.toString().toInt()
                    val num2:Int = editTextText2.text.toString().toInt()
                    val result = (num1 * num2).toString()
                    textView3.text = "총합 : $result"
                }
            }
            // 나누기 버튼
            button4.apply {
                setOnClickListener {
                    val num1:Int = editTextText1.text.toString().toInt()
                    val num2:Int = editTextText2.text.toString().toInt()
                    val result = (num1 / num2).toString()
                    textView3.text = "총합 : $result"
                }
            }
        }
    }
}