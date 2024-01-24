package kr.co.lion.ex07_recyclerview

import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.ex07_recyclerview.databinding.ActivityMainBinding
import kr.co.lion.ex07_recyclerview.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // RecyclerView를 구성하기 위한 데이터를 담을 리스트
    val listRow1 = mutableListOf<String>()
    val listRow2 = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // setTestData()
        initView()
        setViewEvent()

    }

    // 테스트용 데이터 셋팅
    fun setTestData(){
        // RecyclerView 구성을 위한 임시 데이터 셋팅
        listRow1.addAll(arrayOf("문자열1-1", "문자열1-2", "문자열1-3"))
        listRow2.addAll(arrayOf("문자열2-1", "문자열2-2", "문자열2-3"))
    }

    // View를 초기화하는 메서드
    fun initView(){
        activityMainBinding.apply {
            // RecyclerView 설정
            recyclerViewResult.apply {
                // 어댑터 객체 생성
                adapter = RecyclerViewAdapter()
                // 레이아웃 매니저 객체 생성
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }

    }
    // 이벤트를 설정하는 메서드
    fun setViewEvent(){
        activityMainBinding.apply {
            // 버튼 이벤트
            buttonSubmit.setOnClickListener {
                // 사용자가 입력한 내용을 리스트에 담는다.
                listRow1.add(textFieldUserId.text!!.toString())
                listRow2.add(textFieldUserName.text!!.toString())

                // 입력 요소를 비워준다.
                textFieldUserId.setText("")
                textFieldUserName.setText("")

                // 리사이클러 뷰를 갱신한다.
                recyclerViewResult.adapter?.notifyDataSetChanged()
            }
        }
    }

    // 리사이클러 뷰의 어댑터
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        // ViewHolder
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            val rowBinding:RowBinding

            init {
                this.rowBinding = rowBinding
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return listRow1.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.textViewRow1.text = listRow1[position]
            holder.rowBinding.textViewRow2.text = listRow2[position]
        }
    }
}