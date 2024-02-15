package kr.co.lion.ex13_sqlitedatabase2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.ex13_sqlitedatabase2.databinding.FragmentInputBinding


class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                // 메뉴
                inflateMenu(R.menu.input_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemInputDone -> {

                            val name = textInputName.text.toString()
                            val age = textInputAge.text.toString().toInt()
                            val kor = textInputKor.text.toString().toInt()
                            val student = StudentModel(0,name,age,kor)
                            StudentDAO.insertStudent(context,student)


                            // 이전 화면으로 돌아간다.
                            mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
                        }
                    }

                    true
                }
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
                }
            }
        }
    }

}