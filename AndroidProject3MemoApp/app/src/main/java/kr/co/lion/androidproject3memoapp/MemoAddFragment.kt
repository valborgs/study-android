package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kr.co.lion.androidproject3memoapp.databinding.FragmentMemoAddBinding
import java.text.SimpleDateFormat
import java.util.Date

class MemoAddFragment : Fragment() {

    lateinit var fragmentMemoAddBinding: FragmentMemoAddBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMemoAddBinding = FragmentMemoAddBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingTextField()

        return fragmentMemoAddBinding.root
    }

    // 툴바 설정 메서드
    fun settingToolbar(){
        fragmentMemoAddBinding.apply {
            toolbarMemoAdd.apply {
                // 타이틀
                title = "메모 추가"
                // Back
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    // 현재 Fragment를 BackStack에서 제거하여 이전 화면이 보이게 한다.
                    mainActivity.removeFragment(FragmentName.MEMO_ADD_FRAGMENT)
                }
                // 메뉴
                inflateMenu(R.menu.memo_add_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 완료
                        R.id.menuItemMemoAddDone -> {
                            // 유효성 검사 메서드를 호출한다
                            val chk = checkTextFieldInput()
                            if(chk == true){
                                // 글을 저장한다.
                                saveMemoData()

                                // 키보드를 내린다.
                                mainActivity.hideSoftInput()

                                // 모두 제대로 입력을 했다면 MemoReadFragment로 가게 한다.
                                // 글 번호를 가져온다.
                                val maxMemoIdx = MemoDao.selectMaxMemoIdx(mainActivity)
                                // 번들에 담아준다.
                                val memoReadBundle = Bundle()
                                memoReadBundle.putInt("memoIdx",maxMemoIdx)
                                mainActivity.replaceFragment(FragmentName.MEMO_READ_FRAGMENT, true, true, memoReadBundle)
                            }
                        }
                        // 초기화
                        R.id.menuItemMemoAddReset -> {
                            // 입력 요소들을 모두 초기화한다.
                            textFieldMemoAddSubject.setText("")
                            textFieldMemoAddText.setText("")
                            textInputLayoutMemoAddSubject.error = null
                            textInputLayoutMemoAddText.error = null
                            // 첫 번째 입력 요소에 포커스를 준다.
                            mainActivity.showSoftInput(textFieldMemoAddSubject)
                        }
                    }
                    true
                }
            }
        }
    }

    // 입력 요소들 설정
    fun settingTextField(){
        fragmentMemoAddBinding.apply {
            // 첫 번째 입력 요소에 포커스를 준다.
            mainActivity.showSoftInput(textFieldMemoAddSubject)

            // 에러메시지가 생긴만큼의 공백을 미리 확보
            textInputLayoutMemoAddSubject.error = " "
            textInputLayoutMemoAddText.error = " "

            textInputLayoutMemoAddSubject.error = null
            textInputLayoutMemoAddText.error = null

            // 에러메시지가 보여지는 상황일 때를 대비하여 무언가 입력하면 에러메시지를 없애준다.
            textFieldMemoAddSubject.addTextChangedListener {
                textInputLayoutMemoAddSubject.error = null
            }
            textFieldMemoAddText.addTextChangedListener {
                textInputLayoutMemoAddText.error = null
            }
        }
    }

    // 유효성 검사 메서드
    // 반환값 : true - 모두 정상적으로 잘 입력한 것, false - 입력에 문제가 있는 것
    fun checkTextFieldInput() : Boolean{
        fragmentMemoAddBinding.apply {
            // 입력하지 않은 입력 요소 중 가장 위에 있는 view를 담을 변수
            var errorView:View? = null

            // 제목
            if(textFieldMemoAddSubject.text.toString().trim().isEmpty()){
                textInputLayoutMemoAddSubject.error = "메모 제목을 입력해주세요"
                errorView = textFieldMemoAddSubject
            }else{
                textInputLayoutMemoAddSubject.error = null
            }

            // 내용
            if(textFieldMemoAddText.text.toString().trim().isEmpty()){
                textInputLayoutMemoAddText.error = "메모 내용을 입력해주세요"
                if(errorView == null){
                    errorView = textFieldMemoAddText
                }
            }else{
                textInputLayoutMemoAddText.error = null
            }

            // 비어 있는 입력 요소가 있다면
            if(errorView != null){
                // 비어 있는 입력 요소에 포커스를 준다.
                mainActivity.showSoftInput(errorView)
                // 메서드의 수행을 중지 시킨다.
                return false
            }
            return true
        }
    }

    // 입력한 데이터를 저장한다.
    fun saveMemoData(){
        fragmentMemoAddBinding.apply {
            // 입력한 문자열 데이터를 추출한다.
            val memoSubject = textFieldMemoAddSubject.text.toString().trim()
            val memoText = textFieldMemoAddText.text.toString().trim()
            // 날짜 데이터를 구한다.
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val memoDate = simpleDateFormat.format(Date())

            // Log.d("test1234","$memoSubject, $memoText, $memoDate")
            // 객체에 담아준다.
            val memoModel = MemoModel(0, memoSubject, memoDate, memoText)
            // 저장한다.
            MemoDao.insertMemoData(mainActivity, memoModel)
        }
    }
}
