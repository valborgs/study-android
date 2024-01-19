package kr.co.lion.ex06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex06.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            setButton.apply {
                setOnClickListener {
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString()
                    val kor = editTextKor.text.toString()
                    val math = editTextMath.text.toString()

                    if(name=="" || age=="" || kor=="" || math==""){
                        textViewResult.text="입력을 완료해주세요."
                    }else{
                        val total = kor.toInt() + math.toInt()
                        val avg = total.toDouble() / 2
                        textViewResult.text = "이름 : ${name}\n나이 : ${age}\n국어 : ${kor}\n수학 : ${math}\n총점 : ${total}\n평균 : ${avg}"
                    }

                }
            }
        }
    }

}