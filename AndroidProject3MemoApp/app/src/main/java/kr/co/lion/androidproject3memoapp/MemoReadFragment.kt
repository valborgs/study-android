package kr.co.lion.androidproject3memoapp

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoReadBinding

class MemoReadFragment : Fragment() {

    lateinit var fragmentMemoReadBinding: FragmentMemoReadBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoReadBinding = FragmentMemoReadBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingTextField()

        return fragmentMemoReadBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentMemoReadBinding.apply {
            toolbarMemoRead.apply {
                // 타이틀
                title = "메모 보기"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    //mainActivity.removeFragment(FragmentName.MEMO_READ_FRAGMENT)
                    backProcess()
                }
                // 메뉴
                inflateMenu(R.menu.memo_read_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 수정
                        R.id.menuItemMemoReadModify -> {
                            mainActivity.replaceFragment(FragmentName.MEMO_MODIFY_FRAGMENT, true, true, null)
                        }
                        // 삭제
                        R.id.menuItemMemoReadDelete -> {
                            // 삭제를 위한 다이얼로그를 띄운다.
                            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                            materialAlertDialogBuilder.setTitle("메모 삭제")
                            materialAlertDialogBuilder.setMessage("메모를 삭제하면 복구할 수 없습니다")
                            materialAlertDialogBuilder.setNegativeButton("취소", null)
                            materialAlertDialogBuilder.setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
                                // MainFragment로 돌아간다.
                                backProcess()
                            }
                            materialAlertDialogBuilder.show()
                        }
                    }
                    true
                }
            }
        }
    }

    // textField의 내용을 설정해준다.
    fun settingTextField(){

        // 글의 인덱스 번호를 가져온다.
        val memoIdx = arguments?.getInt("memoIdx")!!

        // 현재의 글을 가져온다.
        val memoModel = MemoDao.selectMemoDataOne(mainActivity, memoIdx)

        fragmentMemoReadBinding.textFieldMemoReadSubject.setText(memoModel.memoSubject)
        fragmentMemoReadBinding.textFieldMemoReadText.setText(memoModel.memoText)
        fragmentMemoReadBinding.textFieldMemoReadDate.setText(memoModel.memoDate)
    }

    // 뒤로가기 처리
    fun backProcess(){
        // 현재의 MemoReadFragment 제거
        mainActivity.removeFragment(FragmentName.MEMO_READ_FRAGMENT)
        // MemoAddFragment에서 넘어온 경우에는 MemoAddFragment도 BackStack에서 제거한다.
        mainActivity.removeFragment(FragmentName.MEMO_ADD_FRAGMENT)
    }

}