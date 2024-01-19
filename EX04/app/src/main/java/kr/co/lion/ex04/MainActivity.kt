package kr.co.lion.ex04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            
            // 결과 계산 버튼
            buttonResult.setOnClickListener {
                
                // 숫자 입력 안할 시
                if(editTextNumber1.text.isEmpty() || editTextNumber2.text.isEmpty()){
                    textViewResult.text = "숫자를 입력해주세요"
                    return@setOnClickListener
                }

                // 연산자 선택 안할 시
                if(toggleGroup.checkedButtonId == -1){
                    textViewResult.text = "연산자를 선택해주세요"
                    return@setOnClickListener
                }

                // 계산 진행
                val num1 = editTextNumber1.text.toString().toInt()
                val num2 = editTextNumber2.text.toString().toInt()
                var result = 0

                when(toggleGroup.checkedButtonId){
                    R.id.buttonPlus ->{
                        result = num1 + num2
                    }

                    R.id.buttonMinus ->{
                        result = num1 - num2
                    }

                    R.id.buttonMultiply ->{
                        result = num1 * num2
                    }

                    R.id.buttonDivide ->{
                        result = num1 / num2
                    }
                }

                textViewResult.text = "결과는 ${result} 입니다."
            }
        }

    }
}