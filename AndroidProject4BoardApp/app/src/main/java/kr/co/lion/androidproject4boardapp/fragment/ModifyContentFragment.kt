package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentModifyContentBinding

class ModifyContentFragment : Fragment() {

    lateinit var fragmentModifyContentBinding: FragmentModifyContentBinding
    lateinit var contentActivity: ContentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentModifyContentBinding = FragmentModifyContentBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        settingToolbar()

        return fragmentModifyContentBinding.root
    }

    fun settingToolbar(){
        fragmentModifyContentBinding.toolbarModifyContent.apply {
            // 타이틀
            title = "글 수정"
            // 메뉴
            inflateMenu(R.menu.menu_modify_content)
            // Back
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                contentActivity.removeFragment(ContentFragmentName.MODIFY_CONTENT_FRAGMENT)
            }

            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuItemModifyContentCamera -> {}
                    R.id.menuItemModifyContentAlbum -> {}
                    R.id.menuItemModifyContentReset -> {}
                    R.id.menuItemModifyContentDone -> {
                        contentActivity.removeFragment(ContentFragmentName.MODIFY_CONTENT_FRAGMENT)
                    }
                }
                true
            }
        }
    }
}