package kr.co.lion.androidproject4boardapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingView()

        return fragmentLoginBinding.root
    }

    fun settingToolbar(){
        fragmentLoginBinding.apply {
            toolbarLogin.apply {
                title = "로그인"
                inflateMenu(R.menu.menu_main)
            }
        }
    }

    fun settingView(){
        fragmentLoginBinding.apply {
            // 로그인 버튼
            buttonLogin.setOnClickListener {
                val chk = checkTextFieldInput()
                if(chk){
                    Log.d("test1234","로그인 성공")
                }else{
                    Log.d("test1234","로그인 실패")
                }
            }
            // 회원가입 버튼
            buttonJoin.setOnClickListener {
                mainActivity.replaceFragment(FragmentNameMain.JOIN_FRAGMENT,true,true,null)
            }
        }
    }

    fun checkTextFieldInput():Boolean{
        fragmentLoginBinding.apply {
            // 미입력 뷰를 담을 변수
            var errorView:View? = null

            // 아이디
            if(textInputLoginId.text.toString().trim().isEmpty()){
                textInputLayoutLoginId.error = "아이디를 입력해주세요"
                errorView = textInputLoginId
            }else{
                textInputLayoutLoginId.error = null
            }
            
            // 비밀번호
            if(textInputLoginPw.text.toString().trim().isEmpty()){
                textInputLayoutLoginPw.error = "비밀번호를 입력해주세요"
                if(errorView==null){
                    errorView = textInputLoginPw
                }
            }else{
                textInputLayoutLoginPw.error = null
            }

            // 미입력요소가 있다면
            if(errorView != null){
                // 첫번째 미입력요소에 포커스
                mainActivity.showSoftInput(errorView)
                // 로그인 check 결과로 false 반환
                return false
            }
        }
        return true
    }

}