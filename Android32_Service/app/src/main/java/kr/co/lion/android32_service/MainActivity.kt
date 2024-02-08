package kr.co.lion.android32_service

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kr.co.lion.android32_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 서비스 가동을 위해 사용할 Intent
    lateinit var serviceIntent:Intent

    // 서비스 객체의 주소값을 담을 프로퍼티
    var testService: TestService? = null

    // 서비스 접속을 관리하는 매니저
    lateinit var serviceConnectionClass: ServiceConnectionClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // 현재 서비스가 실행중인지 파악한다.
                // 패키지를 포함한 서비스명을 넣어줌
                val chk = isServiceRunning("kr.co.lion.android32_service.TestService")
                // 서비스를 실행하기 위한 Intent 생성
                serviceIntent = Intent(this@MainActivity, TestService::class.java)

                // 서비스가 가동중이 아니라면
                if(chk == false){
                    // 서비스를 가동한다.
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        startForegroundService(serviceIntent)
                    }else{
                        startService(serviceIntent)
                    }
                }


                // 서비스 접속에 성공하면 Service가 가지고 있는 onBind 메서드가 호출된다.
                //     override fun onBind(intent: Intent): IBinder {
                //        return binder
                //    }

                // Service가 가지고 있는 onBind 메서드에서 반환하는 객체를 OS가 받아둔다.

                // Activity에서 서비스에 접속했을 때 지정한 ServiceConnection 객체에 접근한다.
                // 아래의 두 번째 매개변수
                // bindService(serviceIntent, serviceConnectionClass, BIND_AUTO_CREATE)

                // OS가 ServiceConnection이 가지고 있는 onServiceConnected 메서드를 호출한다.
                // 이때, 두 번째 매개변수에 Service가 전달한 객체를 담아준다.
                // override fun onServiceConnected(name: ComponentName?, service: IBinder?)

                // onServiceConnected 메서드에서 Binder 객체를 통해 서비스 객체의 주소 값을 받아서
                // 서비스 객체에 접근할 수 있다.
                //    val binder = service as TestService.LocalBinder
                //    testService = binder.getService()


                // 서비스에 접속한다.
                serviceConnectionClass = ServiceConnectionClass()
                // BIND_AUTO_CREATE : 서비스가 가동 중이 아닐 때 서비스를 가동시키라는 옵션임. 현재는 동작하지 않음.(명시적으로 동작시키도록 해줘야 함) 매개변수로 요구하는 옵션이기때문에 일단 넣어줌
                bindService(serviceIntent, serviceConnectionClass, BIND_AUTO_CREATE)
            }

            button2.setOnClickListener {
                // 실행중인 서비스를 중단시킨다.
                if(::serviceIntent.isInitialized){
                    stopService(serviceIntent)
                }
            }

            button3.setOnClickListener {
                // 서비스에서 값을 가져온다.
                if(testService != null){
                    val value = testService?.getNumber()
                    textView.text = "value : $value"
                }
            }
        }
    }

    // 서비스가 가동중인지 확인하는 메서드
    fun isServiceRunning(name:String) : Boolean{
        // 서비스 관리자를 추출한다.
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        // 현재 실행 중인 서비스들을 가져온다.
        val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
        // 가져온 서비스의 수 만큼 반복한다.
        serviceList.forEach {
            // 현재 서비스의 이름이 동일한지 확인한다.
            if(it.service.className == name){
                return true
            }
        }
        return false
    }

    // 서비스에 접속을 관리하는 클래스
    inner class ServiceConnectionClass : ServiceConnection{
        // 서비스 접속이 성공하게 되면 호출되는 메서드
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            // TestService에서 정의한 onBind메서드가 리턴하는 서비스 객체 binder를 매개변수 service로 받아옴
            // binder는 TestService에서 LocalBinder클래스로 생성된 객체인데 getService() 메서드를 호출하면 TestClass객체의 주소를 받아옴
            // 따라서 TestClass 클래스 내부에 정의한 getNumber 메서드로 value프로퍼티 값을 가져올 수 있다.
            val binder = service as TestService.LocalBinder
            // 서비스 객체를 추출한다.
            testService = binder.getService()

        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            testService = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 서비스가 접속 중이라면 접속을 해제한다.
        if(::serviceConnectionClass.isInitialized == true){
            unbindService(serviceConnectionClass)
        }
    }
}