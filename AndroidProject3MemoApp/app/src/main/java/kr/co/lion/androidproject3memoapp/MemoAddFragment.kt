package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoAddBinding

class MemoAddFragment : Fragment() {

    lateinit var fragmentMemoAddBinding: FragmentMemoAddBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoAddBinding = FragmentMemoAddBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()

        return fragmentMemoAddBinding.root
    }

    // 툴바 설정 메서드
    fun settingToolbar(){
        fragmentMemoAddBinding.apply {
            toolbarMemoAdd.apply {
                // 타이틀
                title = "메모 추가"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    // 현재 Fragment를 BackStack에서 제거하여 이전 화면이 보이게 한다.
                    mainActivity.removeFragment(FragmentName.MEMO_ADD_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.memo_add_menu)
            }
        }
    }
}