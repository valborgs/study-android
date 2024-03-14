package kr.co.lion.androidproject4boardapp.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.androidproject4boardapp.Gender
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.MainFragmentName
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.Tools
import kr.co.lion.androidproject4boardapp.UserState
import kr.co.lion.androidproject4boardapp.dao.UserDao
import kr.co.lion.androidproject4boardapp.databinding.FragmentAddUserInfoBinding
import kr.co.lion.androidproject4boardapp.model.UserModel
import kr.co.lion.androidproject4boardapp.viewmodel.AddUserInfoViewModel

class AddUserInfoFragment : Fragment() {

    lateinit var fragmentAddUserInfoBinding: FragmentAddUserInfoBinding
    lateinit var mainActivity: MainActivity
    lateinit var addUserInfoViewModel: AddUserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // fragmentAddUserInfoBinding = FragmentAddUserInfoBinding.inflate(inflater)
        fragmentAddUserInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_user_info, container, false)
        addUserInfoViewModel = AddUserInfoViewModel()
        fragmentAddUserInfoBinding.addUserInfoViewModel = addUserInfoViewModel
        fragmentAddUserInfoBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingToolbar()
        settingButtonAddUserInfoSubmit()
        settingInputUI()
        settingCheckBox()

        return fragmentAddUserInfoBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentAddUserInfoBinding.apply {
            toolbarAddUserInfo.apply {
                // 타이틀
                title = "회원가입"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainFragmentName.ADD_USER_INFO_FRAGMENT)
                }
            }
        }
    }

    // 가입 버튼
    fun settingButtonAddUserInfoSubmit(){
        fragmentAddUserInfoBinding.buttonAddUserInfoSubmit.apply {
            // 눌렀을 때
            setOnClickListener {
                // 유효성 검사를 수행한다.
                val chk = checkInputForm()
                // 모든 유효성 검사에 통과를 했다면
                if(chk == true){
                    saveUserData()
                }
            }
        }
    }

    // 입력 요소 관련 설정
    fun settingInputUI(){
        addUserInfoViewModel.textFieldAddUserInfoNickName.value = ""
        addUserInfoViewModel.textFieldAddUserInfoAge.value = ""
        addUserInfoViewModel.settingGender(Gender.MALE)

        // 닉네임에 포커스를 준다.
        Tools.showSoftInput(mainActivity, fragmentAddUserInfoBinding.textFieldAddUserInfoNickName)
    }

    // 체크박스 관련 설정
    fun settingCheckBox(){
        // 모든 체크박스를 초기화한다.
        addUserInfoViewModel.checkBoxAddUserInfoHobby1.value = false
        addUserInfoViewModel.checkBoxAddUserInfoHobby2.value = false
        addUserInfoViewModel.checkBoxAddUserInfoHobby3.value = false
        addUserInfoViewModel.checkBoxAddUserInfoHobby4.value = false
        addUserInfoViewModel.checkBoxAddUserInfoHobby5.value = false
        addUserInfoViewModel.checkBoxAddUserInfoHobby6.value = false
    }

    // 입력 요소에 대한 유효성 검사
    fun checkInputForm():Boolean {
        // 입력한 값을 가져온다.
        val userNickname = addUserInfoViewModel.textFieldAddUserInfoNickName.value!!
        val userAge = addUserInfoViewModel.textFieldAddUserInfoAge.value!!

        // 입력하지 않은 것이 있을 경우 경고문을 띄운다.
        if(userNickname.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentAddUserInfoBinding.textFieldAddUserInfoNickName, "닉네임 입력 오류", "닉네임을 입력해주세요")
            return false
        }

        if(userAge.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentAddUserInfoBinding.textFieldAddUserInfoAge, "나이 입력 오류", "나이를 입력해주세요")
            return false
        }

        return true
    }

    // 데이터를 저장하고 이동한다.
    fun saveUserData(){
        CoroutineScope(Dispatchers.Main).launch{
            // 사용자 번호 시퀀스 값을 가져온다.
            val userSequence = UserDao.getUserSequence()
            // Log.d("test1234","userSequence : $userSequence")
            // 시퀀스 값을 1 증가시켜 덮어씌워준다.
            UserDao.updateUserSequence(userSequence + 1)

            // 저장할 데이터를 가져온다.
            val userIdx = userSequence + 1
            val userId = arguments?.getString("joinUserId")!!
            val userPw = arguments?.getString("joinUserPw")!!
            val userNickName = addUserInfoViewModel.textFieldAddUserInfoNickName.value!!
            val userAge = addUserInfoViewModel.textFieldAddUserInfoAge.value!!.toInt()
            val userGender = addUserInfoViewModel.gettingGender().num
            val userHobby1 = addUserInfoViewModel.checkBoxAddUserInfoHobby1.value!!
            val userHobby2 = addUserInfoViewModel.checkBoxAddUserInfoHobby2.value!!
            val userHobby3 = addUserInfoViewModel.checkBoxAddUserInfoHobby3.value!!
            val userHobby4 = addUserInfoViewModel.checkBoxAddUserInfoHobby4.value!!
            val userHobby5 = addUserInfoViewModel.checkBoxAddUserInfoHobby5.value!!
            val userHobby6 = addUserInfoViewModel.checkBoxAddUserInfoHobby6.value!!
            val userState = UserState.USER_STATE_NORMAL.num

            // 저장할 데이터를 객체에 담는다.
            val userModel = UserModel(
                userIdx, userId, userPw, userNickName, userAge, userGender,
                userHobby1, userHobby2, userHobby3, userHobby4, userHobby5, userHobby6, userState
            )

            // 사용자 정보를 저장한다.
            UserDao.insertUserData(userModel)

            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
            materialAlertDialogBuilder.apply {
                setTitle("가입완료")
                setMessage("가입이 완료되었습니다\n로그인해주세요")
                setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.removeFragment(MainFragmentName.ADD_USER_INFO_FRAGMENT)
                    mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
                }
            }
            materialAlertDialogBuilder.show()
        }
    }
}

