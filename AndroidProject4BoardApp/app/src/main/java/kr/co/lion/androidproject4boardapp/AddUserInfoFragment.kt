package kr.co.lion.androidproject4boardapp

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.children
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.androidproject4boardapp.databinding.FragmentAddUserInfoBinding

class AddUserInfoFragment : Fragment() {

    lateinit var fragmentAddUserInfoBinding: FragmentAddUserInfoBinding
    lateinit var mainActivity: MainActivity

    var checkBoxList = mutableListOf<CheckBox>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentAddUserInfoBinding = FragmentAddUserInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingView()
        settingHobbyCheckbox()

        return fragmentAddUserInfoBinding.root
    }

    fun settingToolbar(){
        fragmentAddUserInfoBinding.apply {
            toolbarAddUserInfo.apply {
                title = "회원가입"
                inflateMenu(R.menu.menu_main)
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentNameMain.ADD_USER_INFO_FRAGMENT)
                }
            }
        }
    }

    fun settingView(){
        fragmentAddUserInfoBinding.apply {
            // 유효성 검사
            buttonConfirmJoin.setOnClickListener {
                val chk1 = checkTextFieldInput()
                if(chk1){
                    Log.d("test1234","회원가입")
                    mainActivity.removeFragment(FragmentNameMain.ADD_USER_INFO_FRAGMENT)
                    mainActivity.removeFragment(FragmentNameMain.JOIN_FRAGMENT)
                }else{
                    Log.d("test1234","미입력")
                    return@setOnClickListener
                }

            }
        }
    }

    fun checkTextFieldInput():Boolean{
        fragmentAddUserInfoBinding.apply {
            // 미입력 뷰를 담을 변수
            var errorView:View? = null

            // 닉네임
            if(textInputAddNickname.text.toString().trim().isEmpty()){
                textInputLayoutAddNickname.error = "닉네임을 입력해주세요"
                errorView = textInputAddNickname
            }else{
                textInputLayoutAddNickname.error = null
            }

            // 나이
            if(textInputAddAge.text.toString().trim().isEmpty()){
                textInputLayoutAddAge.error = "나이를 입력해주세요"
                if(errorView == null){
                    errorView = textInputAddAge
                }
            }else{
                textInputLayoutAddAge.error = null
            }

            // 미입력요소가 있다면
            if(errorView != null){
                // 첫번째 미입력요소에 포커스
                mainActivity.showSoftInput(errorView)
                // check 결과로 false 반환
                return false
            }

            // 성별
            if(toggleGroupGender.checkedButtonId == -1){
                val dialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                dialogBuilder.setTitle("성별 선택")
                dialogBuilder.setMessage("성별을 선택해주세요")
                dialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    toggleGroupGender.requestFocus()

                }
                dialogBuilder.show()
                return false
            }

        }
        return true
    }

    fun settingHobbyCheckbox(){
        fragmentAddUserInfoBinding.apply {
            checkBoxGroup.children.forEach {
                Log.d("test1234","${it}")
            }

            checkBoxList.add(checkBoxHobbyExercise)
            checkBoxList.add(checkBoxHobbyReading)
            checkBoxList.add(checkBoxHobbyMovie)
            checkBoxList.add(checkBoxHobbyCooking)
            checkBoxList.add(checkBoxHobbyMusic)
            checkBoxList.add(checkBoxHobbyEtc)

            checkBoxHobbyTotal.setOnCheckedChangeListener { checkbox, isChecked ->
                when(isChecked){
                    true -> {
                        checkBoxList.forEach {
                            it.isChecked = true
                        }
                    }
                    false -> {
                        checkBoxList.forEach {
                            it.isChecked = false
                        }
                    }
                }
            }

            checkBoxList.forEach {
                it.setOnCheckedChangeListener { button, isChecked ->
                    when(checkCheckBox()){
                        CheckboxState.ALL_CHECKED -> {
                            (checkBoxHobbyTotal as MaterialCheckBox).checkedState = MaterialCheckBox.STATE_CHECKED
                        }
                        CheckboxState.NOT_ALL_CHECKED -> {
                            (checkBoxHobbyTotal as MaterialCheckBox).checkedState = MaterialCheckBox.STATE_INDETERMINATE
                        }
                        CheckboxState.UNCHECKED -> {
                            (checkBoxHobbyTotal as MaterialCheckBox).checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                    }
                }
            }

        }
    }

    fun checkCheckBox():CheckboxState{
        fragmentAddUserInfoBinding.apply {

            val result = if(checkBoxHobbyExercise.isChecked && checkBoxHobbyReading.isChecked &&
                            checkBoxHobbyMovie.isChecked && checkBoxHobbyCooking.isChecked &&
                            checkBoxHobbyMusic.isChecked && checkBoxHobbyEtc.isChecked){
                CheckboxState.ALL_CHECKED
            }else if(checkBoxHobbyExercise.isChecked || checkBoxHobbyReading.isChecked ||
                        checkBoxHobbyMovie.isChecked || checkBoxHobbyCooking.isChecked ||
                        checkBoxHobbyMusic.isChecked ||checkBoxHobbyEtc.isChecked){
                CheckboxState.NOT_ALL_CHECKED
            }else{
                CheckboxState.UNCHECKED
            }

            return result
        }
    }

}

