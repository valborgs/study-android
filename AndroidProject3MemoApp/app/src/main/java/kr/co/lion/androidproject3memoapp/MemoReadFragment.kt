package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoReadBinding

class MemoReadFragment : Fragment() {

    lateinit var fragmentMemoReadBinding: FragmentMemoReadBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoReadBinding = FragmentMemoReadBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingTextField()

        return fragmentMemoReadBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentMemoReadBinding.apply {
            toolbarMemoRead.apply {
                // 타이틀
                title = "메모 보기"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.MEMO_READ_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.memo_read_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 수정
                        R.id.memuItemMemoReadModify -> {
                            mainActivity.replaceFragment(FragmentName.MEMO_MODIFY_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

    // textField의 내용을 설정해준다.
    fun settingTextField(){
        fragmentMemoReadBinding.textFieldMemoReadSubject.setText("제목입니다")
        fragmentMemoReadBinding.textFieldMemoReadText.setText("내용입니다")
        fragmentMemoReadBinding.textFieldMemoReadDate.setText("2024-10-10")
    }

}