package kr.co.lion.ex09_activity

import android.content.Intent
import android.inputmethodservice.Keyboard.Row
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.ex09_activity.databinding.ActivityMainBinding
import kr.co.lion.ex09_activity.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // InputActivity 실행을 위한 런처
    lateinit var inputActivityLauncher:ActivityResultLauncher<Intent>

    // ReportActivity 실행을 위한 런처, startActivity만 써도 되지만 추후 확장성을 위해 런처를 만들어주기
    lateinit var reportActivityLauncher:ActivityResultLauncher<Intent>

    // ShowInfoActivity 실행을 위한 런처, startActivity만 써도 되지만 추후 확장성을 위해 런처를 만들어주기
    lateinit var showInfoActivityLauncher:ActivityResultLauncher<Intent>

    // 학생정보를 담고 있을 리스트를 생성한다.
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

    // 초기 데이터 셋팅
    fun initData(){


        // InputActivity 등록
        val contract1 = ActivityResultContracts.StartActivityForResult()
        inputActivityLauncher = registerForActivityResult(contract1){
            if(it.resultCode == RESULT_OK){
                if(it.data != null){
                    // 객체를 추출한다.
                    // createFromParcel 메서드가 호출되고 반환하는 객체를 반환해준다.
                    val info1 = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                        it.data!!.getParcelableExtra("obj", InfoClass::class.java)
                    } else {
                        it.data!!.getParcelableExtra<InfoClass>("obj")
                    }

//                    Log.d("test1234", "${info1?.name}")
//                    Log.d("test1234", "${info1?.grade}")
//                    Log.d("test1234", "${info1?.kor}")
//                    Log.d("test1234", "${info1?.eng}")
//                    Log.d("test1234", "${info1?.math}")

                    // 리스트에 객체를 담는다.
                    studentList.add(info1!!)
                    // 리사이클러 뷰를 갱신한다.
                    activityMainBinding.recyclerViewItem.adapter?.notifyDataSetChanged()
                }
            }
        }

        // ReportActivity 등록
        val contract2 = ActivityResultContracts.StartActivityForResult()
        reportActivityLauncher = registerForActivityResult(contract2){

        }

        // ShowInfoActivity 등록
        val contract3 = ActivityResultContracts.StartActivityForResult()
        showInfoActivityLauncher = registerForActivityResult(contract3){

        }
    }

    // Toolbar 설정
    fun initToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                // 타이틀
                title = "학생 정보 관리"
                // 메뉴 설정
                inflateMenu(R.menu.main_menu)
                // 메뉴를 눌렀을 때의 리스너
                setOnMenuItemClickListener {
                    // 선택한 메뉴 아이템의 아이디로 분기한다.
                    when(it.itemId){
                        // 학생 정보 추가
                        R.id.main_menu_item1 -> {
                            // InputActivity를 실행한다.
                            val newIntent = Intent(this@MainActivity, InputActivity::class.java)
                            inputActivityLauncher.launch(newIntent)
                        }
                        // 총점과 평균
                        R.id.main_menu_item2 -> {
                            // ReportActivity를 실행한다.
                            val newIntent = Intent(this@MainActivity, ReportActivity::class.java)

                            // 학생 정보가 저장되어 있는지 여부를 담아준다.
                            val chk1 = if(studentList.size == 0){
                                false
                            } else {
                                true
                            }
                            newIntent.putExtra("chk1",chk1)
                            // 담긴 학생 정보가 있다면 각 값을 구해 담아준다.
                            if(chk1 == true){
                                var korTotal = 0
                                var engTotal = 0
                                var mathTotal = 0

                                studentList.forEach {
                                    korTotal += it.kor
                                    engTotal += it.eng
                                    mathTotal += it.math
                                }

                                val korAvg = korTotal / studentList.size
                                val engAvg = engTotal / studentList.size
                                val mathAvg = mathTotal / studentList.size

                                val allTotal = korTotal + engTotal + mathTotal
                                val allAvg = (allTotal / studentList.size) / 3

                                newIntent.putExtra("korTotal",korTotal)
                                newIntent.putExtra("engTotal",engTotal)
                                newIntent.putExtra("mathTotal",mathTotal)
                                newIntent.putExtra("korAvg",korAvg)
                                newIntent.putExtra("engAvg",engAvg)
                                newIntent.putExtra("mathAvg",mathAvg)
                                newIntent.putExtra("allTotal",allTotal)
                                newIntent.putExtra("allAvg",allAvg)
                            }

                            reportActivityLauncher.launch(newIntent)
                        }
                    }

                    true
                }
            }
        }
    }

    // View 초기 셋팅
    fun initView(){
        activityMainBinding.apply {

            recyclerViewItem.apply {
                // RecyclerView에 어댑터를 설정한다.
                adapter = RecyclerViewAdapter()
                // layoutManager 설정
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

        }
    }

    // RecyclerView의 어댑터
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>(){

        // ViewHolder
        inner class ViewHolderClass(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                // 항목 뷰의 가로 세로 길이 셋팅(빈 공간도 클릭 가능하게)
                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                // 항목을 터치했을 때의 이벤트
                this.rowMainBinding.root.setOnClickListener {
                    // ShowInfoActivity를 실행한다.
                    val newIntent = Intent(this@MainActivity, ShowInfoActivity::class.java)

                    // 선택한 항목 번째의 객체를 Intent에 담아준다.
                    newIntent.putExtra("obj", studentList[adapterPosition])

                    showInfoActivityLauncher.launch(newIntent)
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
            holder.rowMainBinding.textViewRowMainGrade.text = "${studentList[position].grade} 학년"
        }
    }

}