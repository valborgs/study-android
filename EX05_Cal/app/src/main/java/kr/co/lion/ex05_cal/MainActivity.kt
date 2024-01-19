package kr.co.lion.ex05_cal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex05_cal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setEvent()
    }

    // 각 뷰를 초기화 하는 메서드
    fun initView(){

    }
    // 이벤트를 설정하는 메서드
    fun setEvent(){

        activityMainBinding.apply {
            // 더하기 버튼
            buttonPlus.setOnClickListener {
                // 입력한 값들을 가져온다.
                val number1 = editTextNumber1.text.toString().toInt()
                val number2 = editTextNumber2.text.toString().toInt()

                // 계산한다.
                val result = number1 + number2

                // 출력한다.
                textViewResult.text = "결과 : $result"
            }

            // 빼기 버튼
            buttonMinus.setOnClickListener {
                // 입력한 값들을 가져온다.
                val number1 = editTextNumber1.text.toString().toInt()
                val number2 = editTextNumber2.text.toString().toInt()

                // 계산한다.
                val result = number1 - number2

                // 출력한다.
                textViewResult.text = "결과 : $result"
            }

            // 더하기 버튼
            buttonMultiply.setOnClickListener {
                // 입력한 값들을 가져온다.
                val number1 = editTextNumber1.text.toString().toInt()
                val number2 = editTextNumber2.text.toString().toInt()

                // 계산한다.
                val result = number1 * number2

                // 출력한다.
                textViewResult.text = "결과 : $result"
            }

            // 나누기 버튼
            buttonDivide.setOnClickListener {
                // 입력한 값들을 가져온다.
                val number1 = editTextNumber1.text.toString().toInt()
                val number2 = editTextNumber2.text.toString().toInt()

                // 계산한다.
                val result = number1 / number2

                // 출력한다.
                textViewResult.text = "결과 : $result"
            }
        }

    }
}