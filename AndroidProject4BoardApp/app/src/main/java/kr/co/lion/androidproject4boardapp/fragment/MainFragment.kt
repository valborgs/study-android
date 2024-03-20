package kr.co.lion.androidproject4boardapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.androidproject4boardapp.ContentActivity
import kr.co.lion.androidproject4boardapp.ContentFragmentName
import kr.co.lion.androidproject4boardapp.MainActivity
import kr.co.lion.androidproject4boardapp.R
import kr.co.lion.androidproject4boardapp.Tools
import kr.co.lion.androidproject4boardapp.dao.ContentDao
import kr.co.lion.androidproject4boardapp.dao.UserDao
import kr.co.lion.androidproject4boardapp.databinding.FragmentMainBinding
import kr.co.lion.androidproject4boardapp.databinding.RowMainBinding
import kr.co.lion.androidproject4boardapp.model.ContentModel
import kr.co.lion.androidproject4boardapp.model.UserModel

class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var contentActivity: ContentActivity

    // 메인 화면의 RecyclerView 구성을 위한 리스트
    var mainList = mutableListOf<ContentModel>()
    // 검색 화면의 RecyclerView 구성을 위한 리스트
    var searchList = mutableListOf<ContentModel>()
    // 사용자 정보를 담고 있을 리스트
    var userList = mutableListOf<UserModel>()

    // 게시판 종류를 담을 프로퍼티
    var contentType = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMainBinding = FragmentMainBinding.inflate(inflater)
        contentActivity = activity as ContentActivity

        // 게시판 종류값을 담아준다.
        contentType = arguments?.getInt("TypeNumber")!!

        settingToolbar()
        settingRecyclerViewMain()
        settingRecyclerViewMainSearch()
        settingSearchBar()
        gettingMainData()

        return fragmentMainBinding.root
    }

    // 툴바 설정
    fun settingToolbar(){
        fragmentMainBinding.apply {
            toolbarMain.apply {
                // 타이틀
                title = arguments?.getString("TypeName")

                // 메뉴
                inflateMenu(R.menu.menu_content_main)
                setNavigationIcon(R.drawable.menu_24px)
                setNavigationOnClickListener {
                    contentActivity.activityContentBinding.drawerLayoutContent.open()
                }
            }
        }
    }

    // SearchView 설정
    fun settingSearchBar(){
        fragmentMainBinding.apply {
            searchBarMain.apply {
                // SearchBar에 보여줄 메시지
                hint = "여기를 눌러 검색해주세요"
                // 메뉴
                inflateMenu(R.menu.menu_main_search_menu)
                setOnMenuItemClickListener {
                    // 글 작성 화면이 나타나게 한다.
                    contentActivity.replaceFragment(ContentFragmentName.ADD_CONTENT_FRAGMENT, true, true, null)
                    true
                }
            }
            
            searchViewMain.apply { 
                // SearchView에 보여줄 메시지
                hint = "검색어를 입력해주세요"
            }
        }
    }

    // 메인 화면의 RecyclerView 설정
    fun settingRecyclerViewMain(){
        fragmentMainBinding.apply {
            recyclerViewMain.apply {
                // 어댑터
                adapter = MainRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(contentActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(contentActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // 검색 화면의 RecyclerView 설정
    fun settingRecyclerViewMainSearch(){
        fragmentMainBinding.apply {
            recyclerViewMainSearch.apply {
                // 어댑터
                adapter = SearchRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(contentActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(contentActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    // 메인 화면의 RecyclerView의 어댑터
    inner class MainRecyclerViewAdapter : RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>(){
        inner class MainViewHolder(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val mainViewHolder = MainViewHolder(rowMainBinding)
            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return mainList.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            holder.rowMainBinding.textViewRowMainSubject.text = mainList[position].contentSubject

            // 사용자의 수 만큼 반복한다.
            userList.forEach {
                // 사용자 번호와 작성자 번호가 같으면 출력하고 중단한다.
                if(it.userIdx == mainList[position].contentWriterIdx){
                    holder.rowMainBinding.textViewRowMainNickName.text = it.userNickName
                    return@forEach
                }
            }

            // 항목을 눌렀을 때 동작할 리스너를 연결해준다.
            holder.rowMainBinding.root.setOnClickListener {
                // 필요한 데이터를 담아준다.
                val readBundle = Bundle()
                readBundle.putInt("contentIdx", mainList[position].contentIdx)
                // 글 읽는 화면으로 이동한다.
                contentActivity.replaceFragment(ContentFragmentName.READ_CONTENT_FRAGMENT, true, true, readBundle)
            }
        }
    }

    // 검색 화면의 RecyclerView의 어댑터
    inner class SearchRecyclerViewAdapter : RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder>(){
        inner class SearchViewHolder(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            val rowMainBinding:RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val mainViewHolder = SearchViewHolder(rowMainBinding)
            return mainViewHolder
        }

        override fun getItemCount(): Int {
            return 100
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.rowMainBinding.textViewRowMainSubject.text = "제목 $position"
            holder.rowMainBinding.textViewRowMainNickName.text = "닉네임 $position"
        }
    }

    // 현재 게시판의 데이터를 가져와 메인 화면의 리사이클러뷰를 갱신한다.
    fun gettingMainData(){
        CoroutineScope(Dispatchers.Main).launch {
            // 서버에서 데이터를 가져온다.
            mainList = ContentDao.gettingContentList(contentType)
            // 사용자 정보를 가져온다.
            userList = UserDao.getUserAll()
            // 리사이클러뷰를 갱신한다.
            fragmentMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }

}