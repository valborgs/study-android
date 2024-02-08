package kr.co.lion.ex10_project2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kr.co.lion.ex10_project2.databinding.ActivityInputBinding
import kotlin.concurrent.thread

class InputActivity : AppCompatActivity() {

    lateinit var activityInputBinding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityInputBinding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(activityInputBinding.root)

        setToolbar()
        setView()

    }

    // 툴바
    fun setToolbar(){
        activityInputBinding.apply {
            toolbarInput.apply {
                // 타이틀
                title = "학생 정보 입력"
                // Back버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 메뉴
                inflateMenu(R.menu.menu_input)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_input_done -> {
                            processInputDone()
                        }
                    }
                    true
                }
            }
        }
    }

    // View 설정
    fun setView(){
        activityInputBinding.apply {
            // 뷰에 포커스를 준다.
            // 키보드를 올린다.
            // 이때 View를 지정해줘야 한다.
            showSoftInput(textFieldInputName)

            // 수학점수 입력칸
            // 엔터키를 누르면 입력 완료처리를 한다.
            textFieldInputMath.setOnEditorActionListener { v, actionId, event ->
                processInputDone()
                true
            }
        }
    }

    // 입력 완료 처리
    fun processInputDone(){
        // Toast.makeText(this@InputActivity,"눌러졌습니다",Toast.LENGTH_SHORT).show()

        activityInputBinding.apply {
            // 사용자가 입력한 내용을 가져온다
            val name = textFieldInputName.text.toString()
            val gradeStr = textFieldInputGrade.text.toString()
            val korStr = textFieldInputKor.text.toString()
            val engStr = textFieldInputEng.text.toString()
            val mathStr = textFieldInputMath.text.toString()

            // 입력 검사
            if(name.isEmpty()){
                showDialog("이름 입력 오류", "이름을 입력해주세요", textFieldInputName)
                return
            }
            if(gradeStr.isEmpty()){
                showDialog("학년 입력 오류", "학년을 입력해주세요", textFieldInputGrade)
                return
            }
            if(korStr.isEmpty()){
                showDialog("국어점수 입력 오류", "국어점수를 입력해주세요", textFieldInputKor)
                return
            }
            if(engStr.isEmpty()){
                showDialog("영어점수 입력 오류", "영어점수를 입력해주세요", textFieldInputEng)
                return
            }
            if(mathStr.isEmpty()){
                showDialog("수학점수 입력 오류", "수학점수를 입력해주세요", textFieldInputMath)
                return
            }

            // 입력받은 정보를 객체에 담아 준다.
            val studentData = StudentData(name, gradeStr.toInt(), korStr.toInt(), engStr.toInt(), mathStr.toInt())

            Snackbar.make(activityInputBinding.root, "등록이 완료되었습니다", Snackbar.LENGTH_SHORT).show()
            // 이전으로 돌아간다.
            val resultIntent = Intent()
            resultIntent.putExtra("studentData",studentData)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    // 다이얼로그를 보여주는 메서드
    fun showDialog(title:String, message:String, focusView:TextInputEditText){
        // 다이얼로그를 보여준다.
        val builder = MaterialAlertDialogBuilder(this@InputActivity).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                focusView.setText("")
                focusView.requestFocus()
                showSoftInput(focusView)
            }
        }

        builder.show()
    }

    // 포커스를 주고 키보드를 올려주는 메서드
    fun showSoftInput(focusView:TextInputEditText){
        focusView.requestFocus()

        thread {
            SystemClock.sleep(200)
            val inputmethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputmethodManager.showSoftInput(focusView, 0)
        }
    }
}