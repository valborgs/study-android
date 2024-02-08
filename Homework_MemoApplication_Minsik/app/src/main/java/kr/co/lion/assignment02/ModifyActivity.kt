package kr.co.lion.assignment02

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.assignment02.databinding.ActivityModifyBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var activityModifyBinding: ActivityModifyBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(activityModifyBinding.root)

        setToolbar()
        initView()
        setEvent()
    }



    fun setToolbar(){
        // 툴바 셋팅
        activityModifyBinding.apply {
            toolbarModify.apply {
                // 타이틀
                setTitle("메모 수정")
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
                            // 수정할 메모
                            val memo = Util.memoList[intent.getIntExtra("position",-1)]
                            memo.title = textInputModifyTitle.text.toString()
                            memo.content = textInputModifyContent.text.toString()
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
        val title = activityModifyBinding.textInputModifyTitle.text.toString().trim()
        val content = activityModifyBinding.textInputModifyContent.text.toString().trim()
        if(title==""){
            Util.showDialog("제목을 입력해주세요.",activityModifyBinding.textInputModifyTitle,this@ModifyActivity)
            return true
        }
        if(content==""){
            Util.showDialog("내용을 입력해주세요.",activityModifyBinding.textInputModifyContent,this@ModifyActivity)
            return true
        }
        return false
    }

    fun initView(){
        // 수정할 메모
        val memo = Util.memoList[intent.getIntExtra("position",-1)]
        activityModifyBinding.apply {
            textInputModifyTitle.setText(memo.title)
            textInputModifyContent.setText(memo.content)
        }
    }

    fun setEvent(){
        activityModifyBinding.apply {
            // 제목 입력칸에 포커스를 준다.
            Util.showSoftInput(activityModifyBinding.textInputModifyTitle,this@ModifyActivity)
        }

    }
}