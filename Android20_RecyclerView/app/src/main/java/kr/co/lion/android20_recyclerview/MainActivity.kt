package kr.co.lion.android20_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.android20_recyclerview.databinding.ActivityMainBinding
import kr.co.lion.android20_recyclerview.databinding.RowBinding

// AdapterView : 무한개의 항목을 보여주는 목적으로 사용하는 뷰들
// Adapter를 사용하기 때문에 AdapterView라고 부른다.

// Adapter : View를 구성하기 위해 필요한 정보를 가지고 있는 요소

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 이미지의 id
    var imageRes = arrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8
    )
    
    // 문자열1
    val textData1 = arrayOf(
        "토고", "프랑스", "스위스", "스페인", "일본", "독일", "브라질", "대한민국"
    )

    // 문자열2
    val textData2 = arrayOf(
        "탈락", "진출", "탈락", "진출", "탈락", "진출", "진출", "진출"
    )

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

            // RecyclerView Decoration

            // 기본 구분선 넣기
            // val deco = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            // recyclerView.addItemDecoration(deco)

            // Material3 구분선 Divder 넣기 (구분선 범위를 직접 지정할 수 있음)
            val deco = MaterialDividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            // 구분선 좌측 여백
            // deco.dividerInsetStart = 450
            // 구분선 우측 여백
            // deco.dividerInsetEnd = 200
            recyclerView.addItemDecoration(deco)
        }
    }

    // Adapter
    // RecyclerView 구성을 위해 필요한 코드들이 작성되어 있다.
    // 1. 클래스를 작성한다
//    class RecyclerViewAdapter{
//
//    }

    // 2. ViewHolder 클래스를 작성해준다.
    // ViewHolder : View의 id를 가지고 있는 요소
//    class RecyclerViewAdapter{
//
//        //ViewHolder
//        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
//            // 매개변수로 들어오는 바인딩객체를 담을 프로퍼티
//            var rowBinding:RowBinding
//
//            init{
//                this.rowBinding = rowBinding
//            }
//        }
//    }

    // 3. adapter 클래스를 Adapter를 상속받게 구현해준다.
    // 필요한 메서드들을 구현해준다.
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        //ViewHolder
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root){
            // 매개변수로 들어오는 바인딩객체를 담을 프로퍼티
            var rowBinding:RowBinding

            init{
                this.rowBinding = rowBinding

                // 현재 항목을 누르면 반응하는 리스너
                // adapterPosition 프로퍼티 : 항목의 순서값
                // 사용자가 터치한 항목이 몇 번째 항목인가로 사용한다.
                this.rowBinding.root.setOnClickListener {
                    activityMainBinding.textView.text = "선택한 항목 : ${textData1[adapterPosition]}"
                }
                // View의 가로길이는 최대크기로 맞춰주어야 항목 옆의 공백을 눌러도 클릭이 된다.
                // 직접 코드로 셋팅을 해줘야 한다.
                this.rowBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // 가로길이
                    ViewGroup.LayoutParams.WRAP_CONTENT  // 세로길이
                )
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
            return imageRes.size
        }

        // 항목의 View에 보여주고자 하는 데이터를 설정한다.
        // 첫 번째 매개변수 : ViewHolder객체. 재사용 가능한 것이 없다면 onCreateViewHolder 메서드를 호출하고 반환하는 ViewHolder 객체가 들어오고
        // 재사용 가능한 것이 있다면 재사용 가능한 ViewHolder객체가 들어온다.
        // 두 번째 매개변수 : 구성하고자 하는 항목의 순서값(0부터 1씩 증가)
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowBinding.imageViewRow.setImageResource(imageRes[position])
            holder.rowBinding.textViewRow1.text = textData1[position]
            holder.rowBinding.textViewRow2.text = textData2[position]
        }
    }
}