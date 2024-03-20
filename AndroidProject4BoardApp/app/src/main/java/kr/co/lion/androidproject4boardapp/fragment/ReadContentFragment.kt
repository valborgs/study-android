package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.ContentType
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.dao.ContentDao
import kr.co.lion.androidproject4boardapp.dao.UserDao
import kr.co.lion.androidproject4boardapp.databinding.FragmentReadContentBinding
import kr.co.lion.androidproject4boardapp.viewmodel.ReadContentViewModel


class ReadContentFragment : Fragment() {

    lateinit var fragmentReadContentBinding: FragmentReadContentBinding
    lateinit var contentActivity: ContentActivity

    lateinit var readContentViewModel: ReadContentViewModel

    // 현재 글 번호를 담을 변수
    var contentIdx = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        // fragmentReadContentBinding = FragmentReadContentBinding.inflate(inflater)
        fragmentReadContentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_read_content, container, false)
        readContentViewModel = ReadContentViewModel()
        fragmentReadContentBinding.readContentViewModel = readContentViewModel
        fragmentReadContentBinding.lifecycleOwner = this

        contentActivity = activity as ContentActivity

        // 글 번호를 담는다.
        contentIdx = arguments?.getInt("contentIdx")!!

        settingToolbarReadContent()
        settingBackButton()
        settingInputForm()

        return fragmentReadContentBinding.root
    }

    // 툴바 설정
    fun settingToolbarReadContent(){
        fragmentReadContentBinding.apply {
            toolbarReadContent.apply {
                // 타이틀
                title = "글 읽기"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
                // 메뉴
                inflateMenu(R.menu.menu_read_content)
                setOnMenuItemClickListener {
                    // 메뉴의 id로 분기한다.
                    when(it.itemId){
                        // 댓글
                        R.id.menuItemReadContentReply -> {
                            // 댓글을 보여줄 BottomSheet를 띄운다.
                            showReplyBottomSheet()
                        }
                        // 수정하기
                        R.id.menuItemReadContentModify -> {
                            // 수정 화면이 보이게 한다.
                            contentActivity.replaceFragment(ContentFragmentName.MODIFY_CONTENT_FRAGMENT, true, true, null)
                        }
                        // 삭제하기
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
            backProcesss()
            // 뒤로가기 버튼의 콜백을 제거한다.
            remove()
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        contentActivity.removeFragment(ContentFragmentName.READ_CONTENT_FRAGMENT)
        contentActivity.removeFragment(ContentFragmentName.ADD_CONTENT_FRAGMENT)
    }

    // 댓글을 보여불 BottomSheet를 띄워준다.
    fun showReplyBottomSheet(){
        val readContentBottomFragment = ReadContentBottomFragment()
        readContentBottomFragment.show(contentActivity.supportFragmentManager, "ReplyBottomSheet")
    }

    // 입력 요소에 값을 넣어준다.
    fun settingInputForm(){

        // 이미지뷰를 안보이게 한다.
        fragmentReadContentBinding.imageViewReadContent.visibility = View.INVISIBLE
        // 입력 요소에 띄어쓰기를 넣어준다.
        readContentViewModel.textFieldReadContentSubject.value = " "
        readContentViewModel.textFieldReadContentText.value = " "
        readContentViewModel.textFieldReadContentDate.value = " "
        readContentViewModel.textFieldReadContentType.value = " "
        readContentViewModel.textFieldReadContentNickName.value = " "


        CoroutineScope(Dispatchers.Main).launch {

            // 현재 글 번호에 해당하는 글 데이터를 가져온다.
            val contentModel = ContentDao.selectContentData(contentIdx)
            // 글을 작성한 사용자의 번호를 통해 사용자 정보를 가져온다.
            val userModel = UserDao.gettingUserInfoByUserIdx(contentModel?.contentWriterIdx!!)

            // 가져온 데이터를 보여준다.
            readContentViewModel.textFieldReadContentSubject.value = contentModel?.contentSubject

            readContentViewModel.textFieldReadContentType.value = when(contentModel?.contentType){
                ContentType.TYPE_FREE.number -> ContentType.TYPE_FREE.str
                ContentType.TYPE_HUMOR.number -> ContentType.TYPE_HUMOR.str
                ContentType.TYPE_SOCIETY.number -> ContentType.TYPE_SOCIETY.str
                ContentType.TYPE_SPORTS.number -> ContentType.TYPE_SPORTS.str
                else -> ""
            }

            readContentViewModel.textFieldReadContentNickName.value = userModel?.userNickName
            readContentViewModel.textFieldReadContentDate.value = contentModel?.contentWriteDate
            readContentViewModel.textFieldReadContentText.value = contentModel?.contentText

            // 이미지 데이터를 불러온다.
            if(contentModel?.contentImage != null) {
                delay(1000L)
                ContentDao.gettingContentImage(contentActivity, contentModel.contentImage!!, fragmentReadContentBinding.imageViewReadContent)
            }
        }
    }
}