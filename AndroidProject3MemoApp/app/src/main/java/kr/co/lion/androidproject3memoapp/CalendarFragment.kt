package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.androidproject3memoapp.databinding.FragmentCalendarBinding
import kr.co.lion.androidproject3memoapp.databinding.RowCalendarBinding
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalendarFragment : Fragment() {

    lateinit var fragmentCalendarBinding: FragmentCalendarBinding
    lateinit var mainActivity: MainActivity

    // 리사이클러뷰 구성을 위한 리스트
    var memoList = mutableListOf<MemoModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentCalendarBinding = FragmentCalendarBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingRecyclerMain()
        settingButtonMainToday()
        settingCalendarMain()

        return fragmentCalendarBinding.root
    }

    // 오늘 버튼 설정
    fun settingButtonMainToday(){
        fragmentCalendarBinding.apply {
            buttonMainToday.setOnClickListener {
                // 현재 시간을 Long값으로 구해 CalendarView에 설정해준다.
                // currentTimeMillis 메서드를 호출한 시점의 값을 Long타입으로 반환해준다.
                calendarMain.date = System.currentTimeMillis()
                // MainActivity의 프로퍼티에 넣어준다.
                mainActivity.calendarNowTime = calendarMain.date
                // 리사이클러뷰를 갱신한다.
                getMemoDataDate()
            }
        }
    }

    // 캘린더 설정
    fun settingCalendarMain(){
        fragmentCalendarBinding.apply {
            calendarMain.apply {
                // 캘린더의 날짜를 MainActivity의 프로퍼티 값으로 지정한다.
                date = mainActivity.calendarNowTime
                // 캘린더의 최대 날짜를 오늘로 설정한다.
                maxDate = System.currentTimeMillis()
                
                // 캘린더 뷰의 날짜가 변경되면 동작하는 리스너
                // 두번째, 세번째, 네번째 : 설정된 년도, 월, 일
                setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // 캘린더의 현재 날짜 값을 MainActivity의 프로퍼티로 넣어준다.
                    // 년월일값을 Long 날짜값으로 변경한다.
                    // 날짜 데이터를 관리하는 객체를 생성하고 새롭게 설정된 날짜 값을 넣어준다.
                    val c1 = Calendar.getInstance()
                    c1.set(year, month, dayOfMonth)
                    // 설정된 날짜값을 Long 형태의 시간값으로 가져와 담아준다.
                    mainActivity.calendarNowTime = c1.timeInMillis

                    // 리사이클러뷰를 갱신한다.
                    getMemoDataDate()
                }
            }
        }
    }

    // RecyclerView 설정
    fun settingRecyclerMain(){
        fragmentCalendarBinding.apply {
            recyclerMain.apply {
                // 어댑터 설정
                adapter = RecyclerMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 데코
                val deco = MaterialDividerItemDecoration(mainActivity,MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)

                // 리사이클러뷰를 갱신한다.
                getMemoDataDate()
            }
        }
    }

    // RecyclerView의 어댑터
    inner class RecyclerMainAdapter : RecyclerView.Adapter<RecyclerMainAdapter.RecyclerMainViewHolder>(){

        inner class RecyclerMainViewHolder(rowCalendarBinding: RowCalendarBinding):RecyclerView.ViewHolder(rowCalendarBinding.root){
            val rowCalendarBinding:RowCalendarBinding

            init {
                this.rowCalendarBinding = rowCalendarBinding
                this.rowCalendarBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerMainViewHolder {
            val rowCalendarBinding = RowCalendarBinding.inflate(layoutInflater)
            val recyclerMainViewHolder = RecyclerMainViewHolder(rowCalendarBinding)
            return recyclerMainViewHolder
        }

        override fun getItemCount(): Int {
            return memoList.size
        }

        override fun onBindViewHolder(holder: RecyclerMainViewHolder, position: Int) {
            holder.rowCalendarBinding.textCalendarSubject.text = memoList[position].memoSubject

            // 항목을 누르면 동작하는 리스너
            holder.rowCalendarBinding.root.setOnClickListener {
                // 메모를 보는 화면이 나타나게 한다.
                val memoReadBundle = Bundle()
                memoReadBundle.putInt("memoIdx", memoList[position].memoIdx)
                mainActivity.replaceFragment(FragmentName.MEMO_READ_FRAGMENT, true, true, memoReadBundle)
            }
        }
    }

    // 캘린더에 설정되어 있는 날짜의 메모 내용을 가져와 리사이클러뷰를 갱신한다.
    fun getMemoDataDate(){
        // 캘린더에 설정되어 있는 날짜 데이터를 년-월-일 형태로 만들어준다
        // 데이터 베이스에 이렇게 저장되어 있기 때문에...
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val targetDate = simpleDateFormat.format(mainActivity.calendarNowTime)
        // 데이터를 가져온다.
        memoList = MemoDao.selectMemoDataDate(mainActivity, targetDate)
        // 리사이클러뷰를 갱신한다.
        fragmentCalendarBinding.recyclerMain.adapter?.notifyDataSetChanged()
    }
}
