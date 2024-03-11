package kr.co.lion.androidproject4boardapp

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class Tools {

    companion object {

        // 뷰에 포커스를 주고 키보드를 올린다.
        fun showSoftInput(context: Context, view: View){
            // 뷰에 포커스를 준다.
            view.requestFocus()
            thread {
                //딜레이를 준다.
                SystemClock.sleep(200)
                // 키보드 관리 객체를 가져온다.
                val inputMethodManager = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 올린다.
                inputMethodManager.showSoftInput(view, 0)
            }
        }

        // 키보드를 내려주고 포커스를 제거한다.
        fun hideSoftInput(activity:Activity){
            // 포커스를 갖고 있는 view가 있다면
            if(activity.window.currentFocus != null){
                // 키보드 관리 객체를 가져온다.
                val inputMethodManager = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                // 키보드를 내려준다.
                inputMethodManager.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken,0)
                // 포커스를 제거해준다.
                activity.window.currentFocus?.clearFocus()
            }
        }

        // 입력 요소가 비어있을 때 보여줄 다이얼로그를 구성하는 메서드
        fun showErrorDialog(context:Context, view: View, title:String, message:String){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle(title)
            materialAlertDialogBuilder.setMessage(message)
            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                showSoftInput(context, view)
            }
            materialAlertDialogBuilder.show()
        }

    }

}

// MainActivity에서 보여줄 프래그먼트들의 이름
enum class MainFragmentName(var str:String){
    LOGIN_FRAGMENT("loginFragment"),
    JOIN_FRAGMENT("joinFragment"),
    ADD_USER_INFO_FRAGMENT("addUserInfoFragment"),
}

// ContentActivity의 Fragments
enum class ContentFragmentName(var str:String){
    MAIN_FRAGMENT("mainFragment"),
    ADD_CONTENT_FRAGMENT("addContentFragment"),
    READ_CONTENT_FRAGMENT("readContentFragment"),
    MODIFY_CONTENT_FRAGMENT("modifyContentFragment"),
    MODIFY_USER_FRAGMENT("modifyUserFragment"),
}

// 남자 또는 여자를 나타내는 값을 정의한다.
enum class Gender(var str:String){
    MALE("male"),
    FEMALE("female")
}