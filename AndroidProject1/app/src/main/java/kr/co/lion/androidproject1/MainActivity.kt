package kr.co.lion.androidproject1

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.androidproject1.databinding.ActivityMainBinding
import kr.co.lion.androidproject1.databinding.FilterDialogBinding
import kr.co.lion.androidproject1.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 동물 등록 런처
    lateinit var activityAddLauncher: ActivityResultLauncher<Intent>
    // 동물 정보 런처
    lateinit var activityShowLauncher: ActivityResultLauncher<Intent>

    // 동물 리스트
    val animalList = mutableListOf<Animal>()

    // 필터링
    var filteredList = mutableListOf<Animal>()
    var filteredPosition = mutableListOf<Int>()
    var filterState="all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        initView()
        setFilter()
        setEvent()
    }
    
    // 런처 셋팅
    private fun setLauncher(){
        
        // 동물 등록
        val contract1 = ActivityResultContracts.StartActivityForResult()
        activityAddLauncher = registerForActivityResult(contract1){
            if(it.resultCode != RESULT_OK) return@registerForActivityResult
            if(it.data != null){
                // 동물 객체를 추출해서 리스트에 담고 리사이클러 뷰 새로고침
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    val animalData = it.data?.getParcelableExtra("animalData",Animal::class.java)
                    animalList.add(animalData!!)
                    setFilter()
                }else{
                    val animalData = it.data?.getParcelableExtra<Animal>("animalData")
                    animalList.add(animalData!!)
                    setFilter()
                }
            }
            // 완료 메시지
            val snackBar = Snackbar.make(activityMainBinding.root,"등록 완료",Snackbar.LENGTH_SHORT)
            snackBar.show()
        }

        // 동물 정보
        val contract2 = ActivityResultContracts.StartActivityForResult()
        activityShowLauncher = registerForActivityResult(contract2){
            when(it.resultCode){
                RESULT_CANCELED -> return@registerForActivityResult
                RESULT_OK -> {
                    // 동물 정보 수정했을 때
                    if(it.data != null){
                        // 동물 객체를 추출해서 리스트에 다시 담고 리사이클러 뷰 새로고침
                        val animalData:Animal = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                            it.data?.getParcelableExtra("newAnimal",Animal::class.java)!!
                        }else{
                            it.data?.getParcelableExtra<Animal>("newAnimal")!!
                        }
                        val animalPosition = it.data?.getIntExtra("animalPosition",-1)
                        animalList[animalPosition!!] = animalData
                        setFilter()
                    }
                }
                RESULT_FIRST_USER ->{
                    // 동물 정보 삭제했을 때
                    if(it.data != null){
                        val deletePosition = it.data!!.getIntExtra("animalPosition",-1)
                        animalList.removeAt(deletePosition)
                        setFilter()
                        // 삭제 메시지
                        val snackBar = Snackbar.make(activityMainBinding.root,"삭제 완료",Snackbar.LENGTH_SHORT)
                        snackBar.show()
                    }
                }
            }
        }
    }

    // 툴바 셋팅
    fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                // 타이틀
                title = "동물원 관리"
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_item_filter -> {
                            setFilterDialog()
                        }
                    }
                    true
                }

            }
        }
    }

    private fun setFilterDialog(){
        val builder = MaterialAlertDialogBuilder(this@MainActivity).apply {
            // 타이틀
            setTitle("필터")

            // 뷰 설정
            val filterDialogBinding = FilterDialogBinding.inflate(layoutInflater)
            setView(filterDialogBinding.root)

            filterDialogBinding.apply {
                // 다이얼로그 불러올 때 filterState에 따라 필터 버튼 체크해놓기
                when(filterState){
                    "all" -> radioButtonFilterAll.performClick()
                    "사자" -> radioButtonFilterLion.performClick()
                    "호랑이" -> radioButtonFilterTiger.performClick()
                    "기린" -> radioButtonFilterGiraff.performClick()
                }
            }

            setNegativeButton("취소", null)
            setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                when(filterDialogBinding.filterGroup.checkedRadioButtonId){
                    R.id.radioButtonFilterAll -> filterState="all"
                    R.id.radioButtonFilterLion -> filterState="사자"
                    R.id.radioButtonFilterTiger -> filterState="호랑이"
                    R.id.radioButtonFilterGiraff -> filterState="기린"
                }
                setFilter()
            }
        }
        builder.show()
    }

    fun setFilter(){
        filteredList.clear()
        filteredPosition.clear()
        animalList.forEachIndexed { index, animal ->
            if(filterState=="all"){
                filteredPosition.add(index)
                filteredList.add(animal)
            }else{
                if(animal.type==filterState){
                    filteredPosition.add(index)
                    filteredList.add(animal)
                }
            }
        }
        activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }

    // View 셋팅
    fun initView(){
        activityMainBinding.apply {
            // 리사이클러 뷰
            recyclerViewMain.apply {
                // 어댑터
                adapter = RecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }

    }

    fun setEvent(){
        activityMainBinding.apply {

            fabAdd.setOnClickListener {
                // AddAnimalActivity 실행
                val addIntent = Intent(this@MainActivity, AddAnimalActivity::class.java)
                activityAddLauncher.launch(addIntent)
            }
        }
    }

    // 리사이클러뷰 어댑터
    inner class RecyclerViewAdapter:RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        inner class ViewHolderClass(rowMainBinding: RowMainBinding):RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding: RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목을 터치했을 때의 이벤트
                this.rowMainBinding.root.setOnClickListener {
                    val infoIntent = Intent(this@MainActivity,ShowAnimalInfoActivity::class.java)
                    infoIntent.putExtra("animalData",filteredList[adapterPosition])
                    infoIntent.putExtra("animalPosition",filteredPosition[adapterPosition])
                    activityShowLauncher.launch(infoIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowMainBinding)
            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return filteredList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMainBinding.textViewRowName.text = filteredList[position].name
            when(filteredList[position].type){
                "사자" -> {
                    holder.rowMainBinding.imageViewRowType.setImageResource(R.drawable.lion)
                }
                "호랑이" -> {
                    holder.rowMainBinding.imageViewRowType.setImageResource(R.drawable.tiger)
                }
                "기린" -> {
                    holder.rowMainBinding.imageViewRowType.setImageResource(R.drawable.giraffe)
                }
                else -> {
                    holder.rowMainBinding.textViewRowName.text = "에러"
                }
            }
        }
    }
}