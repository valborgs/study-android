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
import kr.co.lion.androidproject4boardapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var contentActivity: ContentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        settingToolbar()

        return fragmentMainBinding.root
    }

    fun settingToolbar(){
        fragmentMainBinding.apply {
            toolbarMainFragment.apply {
                // 타이틀
                title = "전체게시판"
                // 메뉴
                inflateMenu(R.menu.menu_content_main)
                // 아이콘
                setNavigationIcon(R.drawable.menu_24px)
                setNavigationOnClickListener {
                    contentActivity.activityContentBinding.drawerLayoutContent.open()
                }

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menuItemContentAdd -> {
                            // 글 작성으로 이동
                            contentActivity.replaceFragment(ContentFragmentName.ADD_CONTENT_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

}