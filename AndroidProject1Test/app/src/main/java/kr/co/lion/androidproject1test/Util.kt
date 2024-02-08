package kr.co.lion.androidproject1test

import android.content.Context
import android.content.DialogInterface
import android.media.Image
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class Util {

    companion object{

        // 동물 객체들을 담을 리스트
        val animalList = mutableListOf<Animal>()

        // 포커스를 주고 키보드를 올려주는 메서드
        fun showSoftInput(view: View, context:Context){
            // 포커스를 준다.
            view.requestFocus()

            thread {
                SystemClock.sleep(1000)
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

        // 안내를 위한 다이얼로그를 보여준다.
        fun showInfoDialog(context: Context, title:String, message:String, listener:(DialogInterface, Int) -> Unit){
            val dialogBuilder = MaterialAlertDialogBuilder(context)
            dialogBuilder.setTitle(title)
            dialogBuilder.setMessage(message)
            dialogBuilder.setPositiveButton("확인", listener)
            dialogBuilder.show()
        }
    }
}

// 동물 종류
enum class AnimalType(var num:Int, var str:String, var img:Int){
    ANIMAL_TYPE_LION(0,"사자", R.drawable.lion),
    ANIMAL_TYPE_TIGER(1,"호랑이", R.drawable.tiger),
    ANIMAL_TYPE_GIRAFFE(2,"기린", R.drawable.giraffe)
}

// 사자 성별
enum class LION_GENDER(var num:Int, var str:String){
    LION_GENDER1(0,"암컷"),
    LION_GENDER2(1,"수컷")
}

// 필터 타입
enum class FilterType(var num:Int, var str:String){
    FILTER_TYPE_ALL(0,"전체"),
    FILTER_TYPE_LION(0,"사자"),
    FILTER_TYPE_TIGER(0,"호랑이"),
    FILTER_TYPE_GIRAFFE(0,"기린")
}