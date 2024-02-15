package kr.co.lion.android34_bottomappbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android34_bottomappbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            // BottomAppBar
            bottomAppBar.apply {
                // Navigation
                setNavigationIcon(R.drawable.menu_24px)
                setNavigationOnClickListener {
                    textView.text = "메뉴를 불렀습니다."
                }
                // Menu
                inflateMenu(R.menu.main_menu)
                setOnMenuItemClickListener {
                    textView.text = when(it.itemId){
                        R.id.item1 -> "메뉴1을 눌렀습니다"
                        R.id.item2 -> "메뉴2을 눌렀습니다"
                        R.id.item3 -> "메뉴3을 눌렀습니다"
                        else -> ""
                    }

                    true
                }

                // FAB
                // layout 파일에서 layout_anchor 속성에 BottomAppBar의 id를 설정해줘야 한다.
                floatingActionButton.setOnClickListener {
                    textView.text = "FloatActionButton을 눌렀습니다."
                }
            }
        }
    }
}