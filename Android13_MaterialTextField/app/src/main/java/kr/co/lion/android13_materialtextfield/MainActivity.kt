package kr.co.lion.android13_materialtextfield

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android13_materialtextfield.databinding.ActivityMainBinding

// Material Text Field
// TextInputLayout 안에 TextInputEditText를 배치하여 사용한다.
// 사용 방법은 일반 EditText와 거의 비슷하고 몇 가지 기능이 추가되어 있다.

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            // 버튼을 눌렀을 때 입력한 내용을 가져와 출력한다.
            button.setOnClickListener {
                var str1 = textField.text.toString()
                textView.text = str1
            }

            // error 설정
            // textField.error = "입력 오류가 발생하였습니다"
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = "입력 오류가 발생하였습니다"
        }
    }
}