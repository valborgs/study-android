package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.MainFragmentName
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentJoinBinding = FragmentJoinBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingButtonJoinNext()
        settingView()

        return fragmentJoinBinding.root
    }
    
    // 툴바 설정
    fun settingToolbar(){
        fragmentJoinBinding.apply {
            toolbarJoin.apply {
                // 타이틀
                title = "회원가입"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    // 이전 화면으로 간다.
                    mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
                }
            }
        }
    }

    // 다음 버튼
    fun settingButtonJoinNext(){
        fragmentJoinBinding.apply {
            buttonJoinNext.apply {
                // 버튼을 눌렀을 때
                setOnClickListener {
                    // AddUserInfoFragment를 보여준다.
                    mainActivity.replaceFragment(MainFragmentName.ADD_USER_INFO_FRAGMENT, true, true, null)
                }
            }
        }
    }

    fun settingView(){
        fragmentJoinBinding.apply {
            // 아이디 중복 확인
            buttonJoinCheckId.setOnClickListener {
                Log.d("test1234","아이디 중복 확인")
            }
            // 다음 버튼
            buttonJoinNext.setOnClickListener {
                Log.d("test1234","미입력 확인")
                val chk1 = checkTextFieldInput()
                if(!chk1) return@setOnClickListener

                Log.d("test1234","비밀번호1,2 일치 확인")
                val chk2 = checkPassword()
                if(!chk2) return@setOnClickListener

                Log.d("test1234","다음 Fragment 이동")
                mainActivity.replaceFragment(MainFragmentName.ADD_USER_INFO_FRAGMENT, true,true,null)
            }
        }
    }

    fun checkTextFieldInput():Boolean{
        fragmentJoinBinding.apply {
            // 미입력 뷰를 담을 변수
            var errorView:View? = null

            // 아이디
            if(textFieldJoinUserId.text.toString().trim().isEmpty()){
                textInputLayoutJoinId.error = "아이디를 입력해주세요"
                errorView = textFieldJoinUserId
            }else{
                textInputLayoutJoinId.error = null
            }

            // 비밀번호 1
            if(textFieldJoinUserPw.text.toString().trim().isEmpty()){
                textInputLayoutJoinPw1.error = "비밀번호 1을 입력해주세요"
                if(errorView==null){
                    errorView = textFieldJoinUserPw
                }
            }else{
                textInputLayoutJoinPw1.error = null
            }

            // 비밀번호 2
            if(textFieldJoinUserPw2.text.toString().trim().isEmpty()){
                textInputLayoutJoinPw2.error = "비밀번호 2를 입력해주세요"
                if(errorView==null){
                    errorView = textFieldJoinUserPw2
                }
            }else{
                textInputLayoutJoinPw2.error = null
            }

            // 미입력요소가 있다면
            if(errorView != null){
                // 첫번째 미입력요소에 포커스
                // mainActivity.showSoftInput(errorView)
                // check 결과로 false 반환
                return false
            }
        }
        return true
    }

    fun checkPassword():Boolean{
        fragmentJoinBinding.apply {
            val pw1 = textFieldJoinUserPw.text.toString().trim()
            val pw2 = textFieldJoinUserPw2.text.toString().trim()
            // 비밀번호1,2가 일치하지 않는다면
            if(pw1 != pw2){
                // mainActivity.showSoftInput(textFieldJoinUserPw2)
                textInputLayoutJoinPw2.error = "비밀번호 1과 일치하지 않습니다"
                // 로그인 check 결과로 false 반환
                return false
            }else{
                textInputLayoutJoinPw2.error = null
            }
        }
        return true
    }

}