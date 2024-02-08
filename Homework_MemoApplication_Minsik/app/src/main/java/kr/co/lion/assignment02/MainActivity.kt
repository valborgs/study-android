package kr.co.lion.assignment02

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat.Builder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.assignment02.databinding.ActivityMainBinding
import kr.co.lion.assignment02.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 메모 작성 런처
    lateinit var activityAddLauncher : ActivityResultLauncher<Intent>
    // 메모 보기 런처
    lateinit var activityInfoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        initView()
        setEvent()
    }

    fun setLauncher() {
        // 메모 작성 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        activityAddLauncher = registerForActivityResult(contract1){
            when(it.resultCode){
                RESULT_OK -> {
                    activityMainBinding.apply {
                        // 리사이클러 뷰 새로고침
                        recyclerViewMain.adapter?.notifyDataSetChanged()
                        // 완료 메시지
                        Snackbar.make(activityMainBinding.root, "메모 작성 완료",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // 메모 보기 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        activityInfoLauncher = registerForActivityResult(contract2){
            when(it.resultCode){
                // 수정했을 때
                RESULT_OK -> {
                    activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                }
                // 삭제했을 때
                RESULT_FIRST_USER -> {
                    activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
                    // 삭제 완료 메시지
                    Snackbar.make(activityMainBinding.root,"삭제 완료",Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun initView(){
        activityMainBinding.apply {

            // 툴바 셋팅
            toolbarMain.apply {
                // 메뉴 타이틀
                setTitle("메모 관리")
                setTitleTextColor(Color.WHITE)

                // 메뉴 레이아웃
                inflateMenu(R.menu.menu_main)

                // 메뉴 버튼 클릭 이벤트
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_main_add -> {
                            // 메모 작성 액티비티 실행
                            val addIntent = Intent(this@MainActivity, AddActivity::class.java)
                            activityAddLauncher.launch(addIntent)
                        }
                    }
                    true
                }
            }

            // 리사이클러 뷰
            recyclerViewMain.apply {
                adapter = RecyclerViewMainAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }



    fun setEvent(){

    }

    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>() {

        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                // 항목 가로 크기 확장
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목 클릭 시 이벤트
                this.rowMainBinding.root.setOnClickListener {
                    val infoIntent = Intent(this@MainActivity,InfoActivity::class.java)
                    infoIntent.putExtra("position",adapterPosition)
                    activityInfoLauncher.launch(infoIntent)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)
            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return Util.memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            holder.rowMainBinding.textViewCardTitle.text = Util.memoList[position].title
            holder.rowMainBinding.textViewCardDate.text = Util.memoList[position].date.toString()
        }
    }

}