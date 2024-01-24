package kr.co.lion.ex08

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.ex08.databinding.ActivityMainBinding
import kr.co.lion.ex08.databinding.RowBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 이름을 담을 리스트
    val nameStore = mutableListOf<String>()

    // 검색 결과를 담을 리스트
    val searchResult = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // View 초기화
        initView()

        // 이벤트 설정
        setViewEvent()
    }

    fun initView(){
        activityMainBinding.apply {
            recyclerView.apply {
                // 어댑터 객체 생성
                adapter = RecyclerViewAdapter()
                // 레이아웃 매니저
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                // 구분선 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
                recyclerView.addItemDecoration(deco)
            }
        }
    }

    fun setViewEvent(){
        activityMainBinding.apply {

            // 처음 있는 테스트 데이터를 보여준다.
            searchResult.addAll(findName(editTextSearch.text.toString()))

            button.setOnClickListener {
                // 사용자가 입력한 이름을 리스트에 담는다.
                nameStore.add(editTextName.text!!.toString())
                // 입력 요소를 비워준다.
                editTextName.setText("")
                editTextSearch.setText("")
                // 리사이클러 뷰 갱신
                recyclerView.adapter?.notifyDataSetChanged()
            }

            editTextSearch.apply{
                addTextChangedListener {
                    // 결과 창 먼저 비워주고
                    searchResult.clear()
                    // 검색 결과 가져오기
                    searchResult.addAll(findName(editTextSearch.text.toString()))
                    // 리사이클러 뷰 갱신
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

        }
    }

    fun findName(searchName:String):MutableList<String>{
        // 검색 결과를 반환할 리스트
        val result = mutableListOf<String>()
        
        // 검색어가 포함된 단어만 찾아서 result 리스트에 추가
        nameStore.forEach {
            if(it.contains(searchName)){
                result.add(it)
            }
        }

        return result
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        // ViewHolder
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            // 매개 변수로 들어오는 바인딩 객체를 담을 프로퍼티
            var rowBinding:RowBinding

            init {
                this.rowBinding = rowBinding
            }
        }

        // ViewHolderClass 객체를 생성하여 반환
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            // View Binding
            val rowBinding = RowBinding.inflate(layoutInflater)
            // View Holder
            val viewHolderClass = ViewHolderClass(rowBinding)

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return searchResult.size
        }

        // 항목에 보여주고자 하는 데이터 설정
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.textView.text = searchResult[position]
        }
    }
}