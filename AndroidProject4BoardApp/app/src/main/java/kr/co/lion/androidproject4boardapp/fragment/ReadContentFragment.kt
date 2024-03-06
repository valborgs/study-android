package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentReadContentBinding

class ReadContentFragment : Fragment() {

    lateinit var fragmentReadContentBinding: FragmentReadContentBinding
    lateinit var contentActivity: ContentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentReadContentBinding = FragmentReadContentBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        settingToolbar()

        return fragmentReadContentBinding.root
    }

    fun settingToolbar(){
        fragmentReadContentBinding.toolbarReadContent.apply {
            // 타이틀
            title = "글 읽기"
            // 메뉴
            inflateMenu(R.menu.menu_read_content)
            // Back
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                contentActivity.removeFragment(ContentFragmentName.READ_CONTENT_FRAGMENT)
            }

            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuItemReadContentModify -> {
                        contentActivity.replaceFragment(ContentFragmentName.MODIFY_CONTENT_FRAGMENT, true, true, null)
                    }
                }

                true
            }
        }
    }
}