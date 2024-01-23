package kr.co.lion.ex06_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.ex06_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initView()
        setViewEvent()
    }

    // 화면 요소에 관련된 초기화
    fun initView(){
        // 취미 스위치는 on 상태로 설정한다.
        activityMainBinding.apply {
            switchHobby.isChecked = true
        }
    }

    // 화면 요소에 대한 이벤트 설정
    fun setViewEvent(){
        activityMainBinding.apply {
            // 취미 스위치 이벤트
            switchHobby.setOnCheckedChangeListener { buttonView, isChecked ->
                // on/off 상태로 분기한다.
                when(isChecked){
                    // on상태라면...
                    true -> {
                        // 활성화
                        checkBoxHobby1.isEnabled = true
                        checkBoxHobby2.isEnabled = true
                        checkBoxHobby3.isEnabled = true
                    }
                    false -> {
                        // 비활성화
                        checkBoxHobby1.isEnabled = false
                        checkBoxHobby1.isChecked = false
                        checkBoxHobby2.isEnabled = false
                        checkBoxHobby2.isChecked = false
                        checkBoxHobby3.isEnabled = false
                        checkBoxHobby3.isChecked = false
                    }
                }
            }

            // 버튼 이벤트
            buttonSubmit.setOnClickListener {
                // 아이디
                textViewResult.text = "아이디 : ${textFieldUserId.text}\n"
                // 비밀번호
                textViewResult.append("비밀번호 : ${textFieldUserPw.text}\n")
                // 사용자 이름
                textViewResult.append("이름 : ${textFieldUserName.text}\n")
                // 취미
                // 스위치의 on/off 상태에 따라 분기
                when(switchHobby.isChecked){
                    // off 상태면 취미가 없는 것으로 취급한다.
                    false -> textViewResult.append("선택한 취미는 없습니다.")
                    // on 상태면 체크박스에 체크한 것을 출력해준다.
                    true -> {
                        // 모든 체크박스가 체크되어 있지 않다면
                        if(checkBoxHobby1.isChecked == false
                            && checkBoxHobby2.isChecked == false
                            && checkBoxHobby3.isChecked == false){
                            textViewResult.append("선택한 취미는 없습니다.")
                        }else{
                            if(checkBoxHobby1.isChecked){
                                textViewResult.append("선택한 취미 : 축구\n")
                            }
                            if(checkBoxHobby2.isChecked){
                                textViewResult.append("선택한 취미 : 농구\n")
                            }
                            if(checkBoxHobby3.isChecked){
                                textViewResult.append("선택한 취미 : 야구\n")
                            }
                        }
                    }
                }
            }
        }
    }
}