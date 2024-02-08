package kr.co.lion.assignment02

import android.content.DialogInterface
import android.graphics.Color
import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kr.co.lion.assignment02.Util.Companion.showSoftInput
import kr.co.lion.assignment02.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        setToolbar()
        initView()
        setEvent()
    }

    fun setToolbar(){
        // 툴바 셋팅
        activityAddBinding.apply {
            toolbarAdd.apply {
                // 타이틀
                setTitle("메모 작성")
                setTitleTextColor(Color.WHITE)
                // 레이아웃
                inflateMenu(R.menu.menu_add)
                // 뒤로가기 버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }
                // 등록 버튼 클릭 시
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_add_confirm -> {
                            if(inputCheck()) return@setOnMenuItemClickListener false
                            val title = textInputAddTitle.text.toString()
                            val content = textInputAddContent.text.toString()
                            val memo = Memo(title,content)
                            Util.memoList.add(memo)
                            setResult(RESULT_OK)
                            finish()
                        }
                    }
                    true
                }

            }
        }
    }

    fun inputCheck():Boolean{
        val title = activityAddBinding.textInputAddTitle.text.toString().trim()
        val content = activityAddBinding.textInputAddContent.text.toString().trim()
        if(title==""){
            Util.showDialog("제목을 입력해주세요.",activityAddBinding.textInputAddTitle,this@AddActivity)
            return true
        }
        if(content==""){
            Util.showDialog("내용을 입력해주세요.",activityAddBinding.textInputAddContent,this@AddActivity)
            return true
        }
        return false
    }

    fun initView(){


    }

    fun setEvent(){
        activityAddBinding.apply {
            // 제목 입력칸에 포커스를 준다.
            Util.showSoftInput(activityAddBinding.textInputAddTitle,this@AddActivity)
        }

    }


}