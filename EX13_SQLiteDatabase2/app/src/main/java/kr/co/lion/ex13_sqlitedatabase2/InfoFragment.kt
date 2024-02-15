package kr.co.lion.ex13_sqlitedatabase2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.ex13_sqlitedatabase2.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    lateinit var fragmentInfoBinding: FragmentInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentInfoBinding = FragmentInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingView()

        return fragmentInfoBinding.root
    }

    // 툴바 셋팅
    fun settingToolbar(){
        fragmentInfoBinding.apply {
            toolbarInfo.apply {
                // 타이틀
                title = "정보 보기"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.INFO_FRAGMENT)
                }
            }
        }
    }

    // View 설정
    fun settingView(){

        // 출력
        fragmentInfoBinding.apply {
            textViewInfo.apply {
                val idx = arguments?.getInt("idx")
                val student = StudentDAO.selectOneStudent(context,idx!!)
                text = "이름 : ${student.name}\n"
                append("나이 : ${student.age}살\n")
                append("국어 점수 : ${student.kor}점")
            }
        }
    }
}
