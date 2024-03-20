package kr.co.lion.androidproject4boardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kr.co.lion.androidproject4boardapp.databinding.ActivityMainBinding
import kr.co.lion.androidproject4boardapp.fragment.AddUserInfoFragment
import kr.co.lion.androidproject4boardapp.fragment.JoinFragment
import kr.co.lion.androidproject4boardapp.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment:Fragment? = null
    var newFragment:Fragment? = null

    // 확인할 권한 목록
    val permissionList = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 스크린이 나타나게 한다.
        // 반드시  setContentView 전에 해야 한다.
        // 코틀린에서는 installSplashScreen 메서드가 구현이 되어있어 호출만 하면 된다.
        installSplashScreen()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 권한 확인
        requestPermissions(permissionList, 0)

        // 자동로그인 시 저장된 사용자 정보를 가져온다.
        val sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE)
        val loginUserIdx = sharedPreferences.getInt("loginUserIdx", -1)
        val loginUserNickName = sharedPreferences.getString("loginUserNickName", null)

        // 자동 로그인 시 저장된 사용자 인덱스값이 없다면(자동로그인을 체크하지 않았다면)
        if(loginUserIdx == -1){
            // 첫 화면을 띄워준다.
            replaceFragment(MainFragmentName.LOGIN_FRAGMENT, false, false, null)
        }
        // 그렇지 않으면
        else {
            // ContentActivity를 실행한다.
            val contentIntent = Intent(this, ContentActivity::class.java)

            // 로그인한 사용자의 정보를 전달해준다.
            contentIntent.putExtra("loginUserIdx", loginUserIdx)
            contentIntent.putExtra("loginUserNickName", loginUserNickName)

            startActivity(contentIntent)
            // MainActivity를 종료한다.
            finish()
        }

    }

    // 지정한 Fragment를 보여주는 메서드
    // name : 프래그먼트 이름
    // addToBackStack : BackStack에 포함 시킬 것인지
    // isAnimate : 애니메이션을 보여줄 것인지
    // data : 새로운 프래그먼트에 전달할 값이 담겨져 있는 Bundle 객체
    fun replaceFragment(name:MainFragmentName, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){

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
            // 로그인 화면
            MainFragmentName.LOGIN_FRAGMENT -> {
                newFragment = LoginFragment()
            }
            // 회원가입 화면1
            MainFragmentName.JOIN_FRAGMENT -> {
                newFragment = JoinFragment()
            }
            // 회원가입 화면2
            MainFragmentName.ADD_USER_INFO_FRAGMENT -> {
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
            fragmentTransaction.replace(R.id.containerMain, newFragment!!)

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
    fun removeFragment(name:MainFragmentName){
        // BackStack에 가장 위에 있는 Fragment를 BackStack에서 제거한다
        // supportFragmentManager.popBackStack()

        // Fragment 전환에 딜레이를 조금 준다.
        SystemClock.sleep(200)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}

