package kr.co.lion.ex11_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.ex11_fragment.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    lateinit var fragmentInfoBinding: FragmentInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentInfoBinding = FragmentInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingView()

        return fragmentInfoBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentInfoBinding.apply {
            toolbarInfo.apply {
                // 타이틀
                title = "정보 보기"
                // menu
                inflateMenu(R.menu.info_menu)

                //뒤로가기 버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    // BackStack에서 Fragment를 제거해 이전 Fragment가 보이도록 한다.
                    mainActivity.removeFragment(FragmentName.INFO_FRAGMENT)
                }
            }
        }
    }

    // View
    fun settingView(){
        // position 값을 가져온다.
        val position = arguments?.getInt("position")!!
        // position번째 객체를 가져온다.
        val studentInfo = mainActivity.studentInfoList[position]
        // 출력
        fragmentInfoBinding.apply {
            textViewInfo.apply {
                text = "이름 : ${studentInfo.name}\n"
                append("나이 : ${studentInfo.age}\n")
                append("국어 점수 : ${studentInfo.kor}\n")
            }
        }
    }

}