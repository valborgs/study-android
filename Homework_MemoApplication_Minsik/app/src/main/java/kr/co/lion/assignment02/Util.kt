package kr.co.lion.assignment02

import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlin.concurrent.thread

class Util {

    companion object {

        // 메모 객체들을 담을 리스트
        val memoList = mutableListOf<Memo>()

        // 포커스를 주고 키보드를 올려주는 메서드
        fun showSoftInput(view: View, context: Context){
            // 포커스를 준다.
            view.requestFocus()

            thread {
                SystemClock.sleep(500)
                val inputMethodManager = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, 0)
            }
        }

        // 키보드를 내려주는 메서드
        fun hideSoftInput(activity:AppCompatActivity){
            // 현재 포커스를 가지고 있는 view가 있다면 키보드를 내린다.
            if(activity.window.currentFocus != null){
                val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken, 0)
            }
        }

        // 다이얼로그
        fun showDialog(message:String, focusView:TextInputEditText, context:Context){
            val builder = MaterialAlertDialogBuilder(context).apply {
                setTitle("입력 확인")
                setMessage(message)
                setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    showSoftInput(focusView,context)
                }
            }
            builder.show()
        }
    }
}