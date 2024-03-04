package kr.co.lion.androidproject4boardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.androidproject4boardapp.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린이 나타나게 한다.
        // 반드시  setContentView 전에 해야 한다.
        // 코틀린에서는 installSplashScreen 메서드가 구현이 되어있어 호출만 하면 된다.
        installSplashScreen()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(FragmentNameMain.LOGIN_FRAGMENT, false, false, null)
    }

    // 지정한 Fragment를 보여주는 메서드
    // name : 프래그먼트 이름
    // addToBackStack : BackStack에 포함 시킬 것인지
    // isAnimate : 애니메이션을 보여줄 것인지
    // data : 새로운 프래그먼트에 전달할 값이 담겨져 있는 Bundle 객체
    fun replaceFragment(name:FragmentNameMain, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

        // Fragment 전환에 딜레이를 조금 준다.
        SystemClock.sleep(200)

        // Fragment를 교체할 수 있는 객체를 추출
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        // var newFragment:Fragment? = null 이 코드는 지워주기

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        // 이름으로 분기한다.
        // Fragment의 객체를 생성하여 변수에 담아준다.
        when(name){
            FragmentNameMain.LOGIN_FRAGMENT -> {
                newFragment = LoginFragment()
            }
            FragmentNameMain.JOIN_FRAGMENT -> {
                newFragment = JoinFragment()
            }
            FragmentNameMain.ADD_USER_INFO_FRAGMENT -> {
                newFragment = AddUserInfoFragment()
            }
        }

        // 새로운 Fragment에 전달할 Bundle 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            // 애니메이션 설정
            if(isAnimate){
                // oldFragment -> newFragment
                // oldFragment : exit
                // newFragment : enter

                // newFragment -> oldFragment
                // oldFragment : reenter
                // newFragment : return

                // MaterialSharedAxis : 좌우, 위아래, 공중 바닥 사이로 이동하는 애니메이션 효과
                // X - 좌우
                // Y - 위아래
                // Z - 공중 바닥
                // 두 번째 매개변수 : 새로운 화면이 나타나는 것인지 여부를 설정
                // true : 새로운 화면이 나타나는 애니메이션이 동작한다.
                // false : 이전으로 되돌아가는 애니메이션이 동작한다.
                if(oldFragment != null){
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈 때 old의 애니메이션
                    oldFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈 때의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            // 첫 번째 매개 변수 : Fragment를 배치할 FragmentContainer의 ID
            // 두 번째 매개 변수 : 보여주고자 하는 Fragment 객체
            fragmentTransaction.replace(R.id.mainContainer, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if(addToBackStack == true){
                // BackStack에 포함시킬 때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }

            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name:FragmentNameMain){
        // BackStack에 가장 위에 있는 Fragment를 BackStack에서 제거한다
        // supportFragmentManager.popBackStack()

        // Fragment 전환에 딜레이를 조금 준다.
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    // 뷰에 포커스를 주고 키보드를 올린다.
    fun showSoftInput(view: View){
        // 뷰에 포커스를 준다.
        view.requestFocus()
        thread {
            //딜레이를 준다.
            SystemClock.sleep(200)
            // 키보드 관리 객체를 가져온다.
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드를 올린다.
            inputMethodManager.showSoftInput(view, 0)
        }
    }

    // 키보드를 내려주고 포커스를 제거한다.
    fun hideSoftInput(){
        // 포커스를 갖고 있는 view가 있다면
        if(window.currentFocus != null){
            // 키보드 관리 객체를 가져온다.
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드를 내려준다.
            inputMethodManager.hideSoftInputFromWindow(window.currentFocus?.windowToken,0)
            // 포커스를 제거해준다.
            window.currentFocus?.clearFocus()
        }
    }
}

