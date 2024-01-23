package kr.co.lion.ex07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.ex07.databinding.ActivityMainBinding
import kr.co.lion.ex07.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    // 아이디 리스트
    val idData = arrayListOf<String>()
    // 이름 리스트
    val nameData = arrayListOf<String>()


    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        activityMainBinding.apply {
            // 어댑터 객체 생성
            val recyclerViewAdapter = RecyclerViewAdapter()
            // 어댑터를 적용해준다.
            recyclerView.adapter = recyclerViewAdapter
            // RecyclerView의 항목을 보여줄 방식을 설정한다.
            // 위에서 아래 방향
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity) // 그냥 this는 activityMainBinding을 의미하게 되므로 MainActivity를 지칭할 수 있게 @를 붙여준다.

            val deco = MaterialDividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(deco)

            button.setOnClickListener {
                val id = textInputId.text.toString()
                val name = textInputName.text.toString()
                if(id!="" && name!=""){
                    idData.add(id)
                    nameData.add(name)
                    // recyclerViewAdapter.notifyDataSetChanged()
                    recyclerViewAdapter.notifyItemInserted(idData.size-1)
                }
            }


        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        //ViewHolder
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            // 매개변수로 들어오는 바인딩객체를 담을 프로퍼티
            var rowBinding: RowBinding

            init{
                this.rowBinding = rowBinding
            }
        }

        // ViewHolder 객체를 생성하여 반환한다.
        // 새롭게 항목이 보여질 때 재사용 가능한 항목이 없다면 이 메서드를 호출한다.
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            // View Binding
            val rowBinding = RowBinding.inflate(layoutInflater)
            // View Holder
            val viewHolderClass = ViewHolderClass(rowBinding)

            // 반환한다.
            return viewHolderClass
        }

        // RecyclerView를 통해 보여줄 항목 전체의 개수를 반환한다.
        override fun getItemCount(): Int {
            return idData.size
        }

        // 항목의 View에 보여주고자 하는 데이터를 설정한다.
        // 첫 번째 매개변수 : ViewHolder객체. 재사용 가능한 것이 없다면 onCreateViewHolder 메서드를 호출하고 반환하는 ViewHolder 객체가 들어오고
        // 재사용 가능한 것이 있다면 재사용 가능한 ViewHolder객체가 들어온다.
        // 두 번째 매개변수 : 구성하고자 하는 항목의 순서값(0부터 1씩 증가)
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.textViewId.text = idData[position]
            holder.rowBinding.textViewName.text = nameData[position]
        }
    }
}