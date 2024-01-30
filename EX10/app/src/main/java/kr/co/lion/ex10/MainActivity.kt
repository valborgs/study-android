package kr.co.lion.ex10

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.ex10.databinding.ActivityMainBinding
import kr.co.lion.ex10.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // InputActivity 실행을 위한 런처
    lateinit var inputActivityLauncher:ActivityResultLauncher<Intent>

    // InfoActivity 실행을 위한 런처
    lateinit var infoActivityLauncher:ActivityResultLauncher<Intent>

    // 학생 정보를 담을 리스트
    val studentList = mutableListOf<InfoClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initData()
        initToolbar()
        initView()
        setEvent()
    }

    fun initData(){

        // InputActivity
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){
            if(it.resultCode == RESULT_OK){
                if(it.data != null){
                    val info1 = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        it.data!!.getParcelableExtra("obj", InfoClass::class.java)
                    }else{
                        it.data!!.getParcelableExtra<InfoClass>("obj")
                    }

                    studentList.add(info1!!)
                    activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
                }
                // 스낵바
                val snackbar = Snackbar.make(activityMainBinding.root, "등록되었습니다", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }

        // InfoActivity
        val contract2 = ActivityResultContracts.StartActivityForResult()
        infoActivityLauncher = registerForActivityResult(contract2){
            if(it.resultCode == RESULT_OK){

            }
        }

    }

    fun initToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                title = "학생 정보 관리"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 학생 정보 추가
                        R.id.main_menu_item1 -> {
                            val newIntent = Intent(this@MainActivity, InputActivity::class.java)
                            inputActivityLauncher.launch(newIntent)
                        }
                    }
                    true
                }
            }
        }
    }

    fun initView(){
        activityMainBinding.apply {
            recyclerView.apply {
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)

                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }

    }

    fun setEvent(){

    }

    // 리사이클러뷰 어댑터
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        // ViewHolder
        inner class ViewHolderClass(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                // 항목 뷰의 가로 세로 길이 셋팅
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목을 터치했을 때의 이벤트
                this.rowMainBinding.root.setOnClickListener { 
                    // InfoActivity 실행
                    val newIntent = Intent(this@MainActivity, InfoActivity::class.java)
                    newIntent.putExtra("obj", studentList[adapterPosition])

                    infoActivityLauncher.launch(newIntent)
                }

                // 항목에 컨텍스트 메뉴 설정
                this.rowMainBinding.root.setOnCreateContextMenuListener { menu, view, menuInfo ->
                    menuInflater.inflate(R.menu.delete_menu,menu)

                    // 항목 삭제
                    menu?.findItem(R.id.delete_menu_item1)?.setOnMenuItemClickListener {
                        studentList.removeAt(adapterPosition)
                        activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
                        true
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowMainBinding)
            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMainBinding.textViewRowMainName.text = studentList[position].name
            holder.rowMainBinding.textViewRowMainGrade.text = "${studentList[position].grade}학년"
        }
    }
}