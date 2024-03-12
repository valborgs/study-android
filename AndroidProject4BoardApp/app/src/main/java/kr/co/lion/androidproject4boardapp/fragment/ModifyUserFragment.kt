package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.Gender
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.Tools
import kr.co.lion.androidproject4boardapp.databinding.FragmentModifyUserBinding
import kr.co.lion.androidproject4boardapp.viewmodel.ModifyUserViewModel

class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var contentActivity: ContentActivity
    lateinit var modifyUserViewModel: ModifyUserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // fragmentModifyUserBinding = FragmentModifyUserBinding.inflate(inflater)
        fragmentModifyUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_user, container, false)
        modifyUserViewModel = ModifyUserViewModel()
        fragmentModifyUserBinding.modifyUserViewModel = modifyUserViewModel
        fragmentModifyUserBinding.lifecycleOwner = this

        contentActivity = activity as ContentActivity

        settingToolbarModifyUser()
        settingInputForm()

        return fragmentModifyUserBinding.root
    }

    fun settingToolbarModifyUser(){
        fragmentModifyUserBinding.toolbarModifyUser.apply {
            // 타이틀
            title = "회원 정보 수정"

            // 네비게이션 아이콘 설정
            setNavigationIcon(R.drawable.menu_24px)
            setNavigationOnClickListener {
                contentActivity.activityContentBinding.drawerLayoutContent.open()
            }

            // 메뉴
            inflateMenu(R.menu.menu_modify_user)
            setOnMenuItemClickListener {
                when(it.itemId){
                    // 초기화
                    R.id.menuItemModifyUserReset -> {
                        settingInputForm()
                    }
                    // 완료
                    R.id.menuItemModifyUserDone -> {
                        // 유효성 감사를 한다.
                        val chk = checkInputForm()
                        if(chk == true){
                            Tools.hideSoftInput(contentActivity)
                        }
                    }
                }
                true
            }
        }
    }

    // 입력 요소 초기화
    fun settingInputForm(){
        modifyUserViewModel.textFieldModifyUserInfoNickName.value = "홍길동"
        modifyUserViewModel.textFieldModifyUserInfoAge.value = "100"
        modifyUserViewModel.textFieldModifyUserInfoPw.value = ""
        modifyUserViewModel.textFieldModifyUserInfoPw2.value = ""

        modifyUserViewModel.settingGender(Gender.FEMALE)

        modifyUserViewModel.checkBoxModifyUserInfoHobby1.value = true
        modifyUserViewModel.checkBoxModifyUserInfoHobby2.value = true
        modifyUserViewModel.checkBoxModifyUserInfoHobby3.value = false
        modifyUserViewModel.checkBoxModifyUserInfoHobby4.value = true
        modifyUserViewModel.checkBoxModifyUserInfoHobby5.value = true
        modifyUserViewModel.checkBoxModifyUserInfoHobby6.value = false

        modifyUserViewModel.onCheckBoxChanged()
    }

    // 입력 요소에 대한 유효성 검사
    fun checkInputForm():Boolean {
        // 입력 요소 값들을 가져온다.
        val nickName = modifyUserViewModel.textFieldModifyUserInfoNickName.value!!
        val age = modifyUserViewModel.textFieldModifyUserInfoAge.value!!
        val pw = modifyUserViewModel.textFieldModifyUserInfoPw.value!!
        val pw2 = modifyUserViewModel.textFieldModifyUserInfoPw2.value!!

        if(nickName.isEmpty()){
            Tools.showErrorDialog(contentActivity, fragmentModifyUserBinding.textFieldModifyUserInfoNickName, "닉네임 입력 오류", "닉네임을 입력해주세요")
            return false
        }

        if(age.isEmpty()){
            Tools.showErrorDialog(contentActivity, fragmentModifyUserBinding.textFieldModifyUserInfoAge, "나이 입력 오류", "나이를 입력해주세요")
            return false
        }

        // 비밀번호 둘 중 하나라도 비어있지 않고 서로 다르다면...
        if((pw.isNotEmpty() || pw2.isNotEmpty()) && pw != pw2){
            modifyUserViewModel.textFieldModifyUserInfoPw.value = ""
            modifyUserViewModel.textFieldModifyUserInfoPw2.value = ""
            
            Tools.showErrorDialog(contentActivity, fragmentModifyUserBinding.textFieldModifyUserInfoPw1, "비밀번호 입력 오류", "비밀번호가 다릅니다")
            return false
        }

        return true
    }

}