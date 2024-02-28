package kr.co.lion.androidproject3memoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.androidproject3memoapp.databinding.FragmentCalendarBinding
import kr.co.lion.androidproject3memoapp.databinding.RowCalendarBinding

class CalendarFragment : Fragment() {

    lateinit var fragmentCalendarBinding: FragmentCalendarBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentCalendarBinding = FragmentCalendarBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingRecyclerMain()

        return fragmentCalendarBinding.root
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
            return 10
        }

        override fun onBindViewHolder(holder: RecyclerMainViewHolder, position: Int) {
            holder.rowCalendarBinding.textCalendarSubject.text = "메모 : $position"

            // 항목을 누르면 동작하는 리스너
            holder.rowCalendarBinding.root.setOnClickListener {
                // 메모를 보는 화면이 나타나게 한다.
                mainActivity.replaceFragment(FragmentName.MEMO_READ_FRAGMENT, true, true, null)
            }
        }
    }

}