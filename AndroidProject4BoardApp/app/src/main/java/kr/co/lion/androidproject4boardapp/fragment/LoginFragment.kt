package kr.co.lion.androidproject4boardapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.MainFragmentName
import kr.co.lion.androidproject4boardapp.R
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
        settingButtonLoginJoin()
        settingButtonLoginSubmit()

        return fragmentLoginBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentLoginBinding.apply {
            toolbarLogin.apply {
                // 타이틀
                title = "로그인"

                inflateMenu(R.menu.menu_main)
            }
        }
    }

    // 회원가입 버튼
    fun settingButtonLoginJoin(){
        fragmentLoginBinding.apply {
            buttonLoginJoin.apply {
                // 버튼을 눌렀을 때
                setOnClickListener {
                    // JoinFragment가 보여지게 한다.
                    mainActivity.replaceFragment(MainFragmentName.JOIN_FRAGMENT, true, true, null)
                }
            }
        }
    }

    // 로그인 버튼
    fun settingButtonLoginSubmit(){
        fragmentLoginBinding.apply {
            buttonLoginSubmit.apply {
                // 버튼을 눌렀을 때
                setOnClickListener {
                    // ContentActivity를 실행한다.
                    val contentIntent = Intent(mainActivity, ContentActivity::class.java)
                    startActivity(contentIntent)
                    // MainActivity를 종료한다.
                    mainActivity.finish()
                }
            }
        }
    }



}