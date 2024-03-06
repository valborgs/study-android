package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentModifyUserBinding

class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var contentActivity: ContentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentModifyUserBinding = FragmentModifyUserBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        settingToolbar()

        return fragmentModifyUserBinding.root
    }

    fun settingToolbar(){
        fragmentModifyUserBinding.toolbarModifyUser.apply {
            // 타이틀
            title = "회원 정보 수정"
            // 메뉴
            inflateMenu(R.menu.menu_modify_user)
            // Back
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                contentActivity.removeFragment(ContentFragmentName.MODIFY_USER_FRAGMENT)
            }

            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuItemModifyUserDone -> {
                        contentActivity.removeFragment(ContentFragmentName.MODIFY_USER_FRAGMENT)
                    }
                }
                true
            }
        }
    }

}