package kr.co.lion.ex11_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.ex11_fragment.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentInputBinding = FragmentInputBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()

        return fragmentInputBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentInputBinding.apply {
            toolbarInput.apply {
                // 타이틀
                title = "정보 입력"
                // menu
                inflateMenu(R.menu.input_menu)
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemInputDone -> {
                            // mainActivity.replaceFragment(FragmentName.INPUT_FRAGMENT, true, true, null)

                            // 입력한 학생 정보를 객체에 담아준다.
                            val name = textInputName.text.toString()
                            val age = textInputAge.text.toString().toInt()
                            val kor = textInputKor.text.toString().toInt()

                            val studentInfo = StudentInfo(name,age,kor)

                            // 리스트에 담아준다.
                            mainActivity.studentInfoList.add(studentInfo)
                            // BackStack에서 Fragment를 제거해 이전 Fragment가 보이도록 한다.
                            mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }

}