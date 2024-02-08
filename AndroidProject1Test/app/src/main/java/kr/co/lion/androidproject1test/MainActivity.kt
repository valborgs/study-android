package kr.co.lion.androidproject1test

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.androidproject1test.databinding.ActivityMainBinding
import kr.co.lion.androidproject1test.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // Activity 런처
    lateinit var inputActivityLauncher : ActivityResultLauncher<Intent>
    lateinit var showActivityLauncher : ActivityResultLauncher<Intent>

    // RecycerView를 구성하기 위한 리스트
    val recyclerViewList = mutableListOf<Animal>()
    // 현재 항목을 구성하기 위해 사용한 객체가 Util.animalList의 몇번째 객체인지를 담을 리스트
    val recyclerViewIndexList = mutableListOf<Int>()
    // 현재 선택되어 있는 필터 타입
    var filterType = FilterType.FILTER_TYPE_ALL
    // 현재 선택되어 있는 필터 타입 - MultiChoice
    var filterTypeMulti = booleanArrayOf(true, true, true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        setView()
        setEvent()
    }

    // 런처 설정
    fun setLauncher(){
        // InputActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){
        }

        // ShowActivity 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        showActivityLauncher = registerForActivityResult(contract2){
        }
    }

    override fun onResume() {
        super.onResume()
        activityMainBinding.apply {
            // 필터 타입에 맞는 데이터를 담는다.
            setRecyclerViewList()

            // 리사이클러 뷰 갱신
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    // 툴바 설정
    fun setToolbar(){

        activityMainBinding.apply {
            toolbarMain.apply {
                // 타이틀
                title = "동물원 관리"
                // 메뉴
                inflateMenu(R.menu.menu_main)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        // 필터 메뉴
                        R.id.menu_item_main_filter -> {
                            // 필터 선택을 위한 다이얼로그를 띄운다.
                            // 기본 다이얼로그
                            // showFilterDialog()
                            // MultiChoice 다이얼로그
                            // showFilterDialogMultiChoice()
                            // SingleChoice 다이얼로그
                            showFilterDialogSingleChoice()
                        }
                    }
                    true
                }
            }
        }
    }

    // View 설정
    fun setView(){
        activityMainBinding.apply {
            // RecyclerView
            recyclerView.apply {
                // 어댑터
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // 이벤트 설정
    fun setEvent(){
        activityMainBinding.apply {
            // FloatActionButton
            fabMainAdd.setOnClickListener {
                // InputActivity를 실행한다.
                val inputIntent = Intent(this@MainActivity, InputActivity::class.java)
                inputActivityLauncher.launch(inputIntent)
            }
        }
    }

    // RecyclerView의 어댑터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding: RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)
            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return recyclerViewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            // position번째 객체를 추출
            val animal = recyclerViewList[position]
            holder.rowMainBinding.textViewRowMainName.text = animal.name
            holder.rowMainBinding.imageViewRowMainType.setImageResource(animal.type.img)

            // 항목을 누르면 ShowActivity를 실행
            holder.rowMainBinding.root.setOnClickListener {
                val showIntent = Intent(this@MainActivity, ShowActivity::class.java)
                // showIntent.putExtra("position",position)
                // 사용자가 선택한 항목을 구성하기 위해 사용한 객체가
                // Util.animalList 리스트에 몇번째에 있는 값인지를 담아준다.
                showIntent.putExtra("position",recyclerViewIndexList[position])
                // 객체를 담아준다.
                val obj = Util.animalList[recyclerViewIndexList[position]]
                // List의 제네릭이 Animal이므로 객체를 추출하면 Animal타입이다.
                // Animal이 Parcelable을 구현했기 때문에 intent에 담을 수 있다.
                showIntent.putExtra("obj",obj)

                showActivityLauncher.launch(showIntent)
            }
        }
    }

    // 필터 다이얼로그를 띄우는 메서드 - basic
    fun showFilterDialog(){
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder.setTitle("필터 선택")
        
        // 항목
        val itemArray = arrayOf("전체","사자","호랑이","기린")
        dialogBuilder.setItems(itemArray){ dialogInterface: DialogInterface, i: Int ->
            // 리스너의 두 번째 매개변수(i)에는 사용자가 선택한 다이얼로그의 항목의 순서값이 전달된다.
            // 선택한 항목에 대한 필터 값을 설정해준다.
            filterType = when(i){
                0 -> FilterType.FILTER_TYPE_ALL
                1 -> FilterType.FILTER_TYPE_LION
                2 -> FilterType.FILTER_TYPE_TIGER
                3 -> FilterType.FILTER_TYPE_GIRAFFE
                else -> FilterType.FILTER_TYPE_ALL
            }
            // 데이터를 새로 담는다.
            setRecyclerViewList()
            // 리사이클러뷰를 갱신한다.
            activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
        }

        dialogBuilder.setNegativeButton("취소",null)
        dialogBuilder.show()
    }

    // 필터 다이얼로그를 띄우는 메서드 - multiChoice
    fun showFilterDialogMultiChoice(){
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder.setTitle("필터 선택")

        // 항목, 전체 선택이 가능하므로 전체 항목은 없애준다.
        val itemArray = arrayOf("사자","호랑이","기린")

        // 두 번째 : 체크상태가 변경된 항목의 순서값
        // 세 번째 : 체크 상태
        dialogBuilder.setMultiChoiceItems(itemArray,filterTypeMulti){ dialogInterface: DialogInterface, i: Int, b: Boolean ->
            // 체크가 변경된 항목 번째의 값을 변경한다.
            filterTypeMulti[i] = b
        }

        dialogBuilder.setNegativeButton("취소",null)
        dialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            // 데이터를 새로 담는다.
            setRecyclerViewList()
            // 리사이클러뷰를 갱신한다.
            activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
        }
        dialogBuilder.show()
    }

    // 필터 다이얼로그를 띄우는 메서드 - singleChoice
    fun showFilterDialogSingleChoice(){
        val dialogBuilder = MaterialAlertDialogBuilder(this@MainActivity)
        dialogBuilder.setTitle("필터 선택")

        // 항목
        val itemArray = arrayOf("전체","사자","호랑이","기린")

        dialogBuilder.setSingleChoiceItems(itemArray, filterType.num) { dialogInterface: DialogInterface, i: Int ->
            // 리스너의 두 번째 매개변수(i)에는 사용자가 선택한 다이얼로그의 항목의 순서값이 전달된다.
            // 선택한 항목에 대한 필터 값을 설정해준다.
            filterType = when(i){
                0 -> FilterType.FILTER_TYPE_ALL
                1 -> FilterType.FILTER_TYPE_LION
                2 -> FilterType.FILTER_TYPE_TIGER
                3 -> FilterType.FILTER_TYPE_GIRAFFE
                else -> FilterType.FILTER_TYPE_ALL
            }
        }

        dialogBuilder.setNegativeButton("취소",null)
        dialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            // 데이터를 새로 담는다.
            setRecyclerViewList()
            // 리사이클러뷰를 갱신한다.
            activityMainBinding.recyclerView.adapter?.notifyDataSetChanged()
        }
        dialogBuilder.show()
    }

    // 검색 필터에 따라 리스트에 데이터를 담아주는 메서드
    fun setRecyclerViewList(){
        // 기본 다이얼로그, SingleChoice 용
        setRecyclerViewListBasic()
        // MultiChoice 다이얼로그용
        // setRecyclerViewListMulti()
    }

    // 기본 다이얼로그, SingleChoice 용
    fun setRecyclerViewListBasic(){
        // 리스트 초기화
        recyclerViewList.clear()
        recyclerViewIndexList.clear()

        // 필터에 따라 분기한다.
        when(filterType){
            // 전체
            FilterType.FILTER_TYPE_ALL -> {
                // 모든 객체를 담아준다.
                var index = 0
                Util.animalList.forEach {
                    recyclerViewList.add(it)
                    recyclerViewIndexList.add(index)
                    index++
                }
            }
            // 사자
            FilterType.FILTER_TYPE_LION -> {
                // 사자만 담아준다.
                var index = 0
                Util.animalList.forEach {
                    if(it.type==AnimalType.ANIMAL_TYPE_LION){
                        recyclerViewList.add(it)
                        recyclerViewIndexList.add(index)
                    }
                    index++
                }
            }
            // 호랑이
            FilterType.FILTER_TYPE_TIGER -> {
                // 호랑이만 담아준다.
                var index = 0
                Util.animalList.forEach {
                    if(it.type==AnimalType.ANIMAL_TYPE_TIGER){
                        recyclerViewList.add(it)
                        recyclerViewIndexList.add(index)
                    }
                    index++
                }
            }
            // 기린
            FilterType.FILTER_TYPE_GIRAFFE -> {
                // 기린만 담아준다.
                var index = 0
                Util.animalList.forEach {
                    if(it.type==AnimalType.ANIMAL_TYPE_GIRAFFE){
                        recyclerViewList.add(it)
                        recyclerViewIndexList.add(index)
                    }
                    index++
                }
            }
        }
    }

    // MultiChoice 다이얼로그용
    fun setRecyclerViewListMulti() {
        // 리스트 초기화
        recyclerViewList.clear()
        recyclerViewIndexList.clear()

        // animalList에 담긴 객체의 수 만큼 반복한다.
        var index = 0
        Util.animalList.forEach {
            // 동물 타입이 사자이고 사자 필터가 true라면 담아준다.
            if(it.type == AnimalType.ANIMAL_TYPE_LION && filterTypeMulti[0] == true){
                recyclerViewList.add(it)
                recyclerViewIndexList.add(index)
            }
            // 동물 타입이 호랑이이고 호랑이 필터가 true라면 담아준다.
            if(it.type == AnimalType.ANIMAL_TYPE_TIGER && filterTypeMulti[1] == true){
                recyclerViewList.add(it)
                recyclerViewIndexList.add(index)
            }
            // 동물 타입이 기린이고 기린 필터가 true라면 담아준다.
            if(it.type == AnimalType.ANIMAL_TYPE_GIRAFFE && filterTypeMulti[2] == true){
                recyclerViewList.add(it)
                recyclerViewIndexList.add(index)
            }
            index++
        }
    }

}