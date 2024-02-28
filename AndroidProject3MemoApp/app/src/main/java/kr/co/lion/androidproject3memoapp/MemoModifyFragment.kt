package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoModifyBinding

class MemoModifyFragment : Fragment() {

    lateinit var fragmentMemoModifyBinding: FragmentMemoModifyBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoModifyBinding = FragmentMemoModifyBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingTextField()

        return fragmentMemoModifyBinding.root
    }

    // 툴바를 구성한다.
    fun settingToolbar(){
        fragmentMemoModifyBinding.apply {
            toolbarMemoModify.apply {
                // 타이틀
                title = "메모 수정"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.MEMO_MODIFY_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.memo_modify_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.memuItemMemoReadModify -> {

                        }
                    }
                    true
                }
            }
        }
    }

    // TextField에 문자열을 설정한다.
    fun settingTextField(){
        fragmentMemoModifyBinding.apply {
            textFieldMemoModifySubject.setText("메모 제목")
            textFieldMemoModifyText.setText("메모 내용")
        }
    }
}