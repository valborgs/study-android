package kr.co.lion.androidproject3memoapp

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoModifyBinding

class MemoModifyFragment : Fragment() {

    lateinit var fragmentMemoModifyBinding: FragmentMemoModifyBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoModifyBinding = FragmentMemoModifyBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingTextField()

        return fragmentMemoModifyBinding.root
    }

    // 툴바를 구성한다.
    fun settingToolbar(){
        fragmentMemoModifyBinding.apply {
            toolbarMemoModify.apply {
                // 타이틀
                title = "메모 수정"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(FragmentName.MEMO_MODIFY_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.memo_modify_menu)
                // 메뉴를 눌렀을 때
                setOnMenuItemClickListener {
                    // 메뉴의 id로 분기한다.
                    when(it.itemId){
                        R.id.menuItemMemoModifyDone -> {
                            // 유효성 검사 메서드를 호출한다.
                            val chk = checkTextField()
                            // 모든 입력 요소가 모두 입력되어 있다면 수정 여부를 묻는 다이얼로그를 띄운다.
                            if(chk==true){
                                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                                materialAlertDialogBuilder.setTitle("수정 확인")
                                materialAlertDialogBuilder.setMessage("수정을 완료하면 이전 내용으로 복구할 수 없습니다.")
                                materialAlertDialogBuilder.setNegativeButton("취소",null)
                                materialAlertDialogBuilder.setPositiveButton("완료"){ dialogInterface: DialogInterface, i: Int ->
                                    // MemoReadFragment로 돌아간다.
                                    mainActivity.removeFragment(FragmentName.MEMO_MODIFY_FRAGMENT)
                                }
                                materialAlertDialogBuilder.show()
                            }
                        }
                        R.id.menuItemMemoModifyReset -> {
                            // 입력 요소 초기화
                            fragmentMemoModifyBinding.apply { 
                                textFieldMemoModifySubject.setText("메모 제목")
                                textFieldMemoModifyText.setText("메모 내용")
                                // 제목에 포커스를 준다.
                                mainActivity.showSoftInput(textFieldMemoModifySubject)
                            }
                        }
                    }
                    true
                }
            }
        }
    }

    // TextField에 문자열을 설정한다.
    fun settingTextField(){
        fragmentMemoModifyBinding.apply {
            textFieldMemoModifySubject.setText("메모 제목")
            textFieldMemoModifyText.setText("메모 내용")
        }
    }

    // 입력 요소에 대한 유효성 검사 처리
    fun checkTextField():Boolean{
        fragmentMemoModifyBinding.apply {

            // 제목이 비어있다면
            if(textFieldMemoModifySubject.text.toString().trim().isEmpty()){
                mainActivity.showErrorDialog(textFieldMemoModifySubject, "제목 입력 오류", "제목을 입력해주세요")
                return false
            }

            // 내용이 비어있다면
            if(textFieldMemoModifyText.text.toString().trim().isEmpty()){
                mainActivity.showErrorDialog(textFieldMemoModifyText, "내용 입력 오류", "내용을 입력해주세요")
                return false
            }

            return true
        }
    }
}