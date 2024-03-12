package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.databinding.FragmentReadContentBinding
import kr.co.lion.androidproject4boardapp.viewmodel.ReadContentViewModel

class ReadContentFragment : Fragment() {

    lateinit var fragmentReadContentBinding: FragmentReadContentBinding
    lateinit var contentActivity: ContentActivity

    lateinit var readContentViewModel: ReadContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // fragmentReadContentBinding = FragmentReadContentBinding.inflate(inflater)
        fragmentReadContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_content, container, false)
        readContentViewModel = ReadContentViewModel()
        fragmentReadContentBinding.readContentViewModel = readContentViewModel
        fragmentReadContentBinding.lifecycleOwner = this

        contentActivity = activity as ContentActivity

        settingToolbar()
        settingBackButton()
        settingInputForm()

        return fragmentReadContentBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentReadContentBinding.apply {
            toolbarReadContent.apply {
                // 타이틀
                title = "글 읽기"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcess()
                }
                // 메뉴
                inflateMenu(R.menu.menu_read_content)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 댓글
                        R.id.menuItemReadContentReply -> {
                            // 댓글을 보여줄 BottomSheet를 띄워준다.
                            showReplyBottomSheet()
                        }
                        // 수정
                        R.id.menuItemReadContentModify -> {
                            // 수정 화면이 보이게 한다.
                            contentActivity.replaceFragment(ContentFragmentName.MODIFY_CONTENT_FRAGMENT, true, true, null)
                        }
                        // 삭제
                        R.id.menuItemReadContentDelete -> {

                        }
                    }
                    true
                }
            }
        }
    }

    // Back button 눌렀을 때
    fun settingBackButton(){
        contentActivity.onBackPressedDispatcher.addCallback {
            // 뒤로가기 처리 메서드 호출
            backProcess()
            // 뒤로가기 버튼의 콜백을 제거한다.
            // 제거를 안하면 이후에도 back버튼을 눌렀을때 이 콜백 내용만 처리됨
            remove()
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        contentActivity.removeFragment(ContentFragmentName.READ_CONTENT_FRAGMENT)
        contentActivity.removeFragment(ContentFragmentName.ADD_CONTENT_FRAGMENT)
    }

    // 댓글을 보여줄 BottomSheet를 띄워준다.
    fun showReplyBottomSheet(){
        val readContentBottomFragment = ReadContentBottomFragment()
        readContentBottomFragment.show(contentActivity.supportFragmentManager, "ReplyBottomSheet")
    }
    
    // 입력 요소에 값을 넣어준다.
    fun settingInputForm(){
        readContentViewModel.textFieldReadContentSubject.value = "제목입니다"
        readContentViewModel.textFieldReadContentType.value = "자유게시판"
        readContentViewModel.textFieldReadContentNickName.value = "홍길동"
        readContentViewModel.textFieldReadContentDate.value = "2024-03-12"
        readContentViewModel.textFieldReadContentText.value = "내용입니다"
    }
}