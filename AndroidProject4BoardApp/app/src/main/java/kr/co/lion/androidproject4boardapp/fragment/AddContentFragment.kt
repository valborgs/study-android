package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentAddContentBinding

class AddContentFragment : Fragment() {

    lateinit var fragmentAddContentBinding: FragmentAddContentBinding
    lateinit var contentActivity: ContentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentAddContentBinding = FragmentAddContentBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        settingToolbarAddContent()

        return fragmentAddContentBinding.root
    }

    // 툴바 셋팅
    fun settingToolbarAddContent(){
        fragmentAddContentBinding.apply {
            toolbarAddContent.apply {
                // 타이틀
                title = "글 작성"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    contentActivity.removeFragment(ContentFragmentName.ADD_CONTENT_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.menu_add_content)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 카메라
                        R.id.menuItemAddContentCamera -> {}
                        // 앨범
                        R.id.menuItemAddContentAlbum -> {}
                        // 초기화
                        R.id.menuItemAddContentReset -> {}
                        // 완료
                        R.id.menuItemAddContentDone -> {
                            // ReadContentFragment로 이동한다.
                            contentActivity.replaceFragment(ContentFragmentName.READ_CONTENT_FRAGMENT, true, true, null)
                        }
                    }

                    true
                }
            }
        }
    }

}