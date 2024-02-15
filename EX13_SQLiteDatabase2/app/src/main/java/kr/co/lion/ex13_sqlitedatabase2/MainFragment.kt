package kr.co.lion.ex13_sqlitedatabase2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.ex13_sqlitedatabase2.databinding.FragmentMainBinding
import kr.co.lion.ex13_sqlitedatabase2.databinding.MainRowBinding

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbar()
        settingView()

        return fragmentMainBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentMainBinding.apply {
            toolbarMain.apply {
                // 타이틀
                title = "학생 관리"
                // menu
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {

                    when(it.itemId){
                        R.id.menuItemMainAdd -> {
                            // InputFragment가 보이게 한다.
                            mainActivity.replaceFragment(FragmentName.INPUT_FRAGMENT, true, true, null)
                        }
                    }

                    true
                }
            }
        }
    }

    // View 설정
    fun settingView(){
        fragmentMainBinding.apply {
            // RecyclerView
            recyclerViewMain.apply {
                // RecyclerView 구성을 위한 리스트
                mainActivity.studentList = StudentDAO.selectAllStudent(context)
                // 어뎁터
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // RecyclerView의 어뎁터 클래스
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>(){
        // ViewHolder
        inner class ViewHolderMain(mainRowBinding: MainRowBinding) : RecyclerView.ViewHolder(mainRowBinding.root){
            val mainRowBinding:MainRowBinding

            init {
                this.mainRowBinding = mainRowBinding
                this.mainRowBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val mainRowBinding = MainRowBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(mainRowBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return mainActivity.studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            // position 번째 학생 객체를 추출한다.
            val student = mainActivity.studentList[position]


            holder.mainRowBinding.textViewMainRowName.text = student.name

            holder.mainRowBinding.root.setOnClickListener {
                // 항목을 누르면 ShowFragment로 이동되게 한다.
                val showBundle = Bundle()
                showBundle.putInt("idx",mainActivity.studentList[position].idx)

                mainActivity.replaceFragment(FragmentName.INFO_FRAGMENT, true, true, showBundle)
            }
        }
    }
}