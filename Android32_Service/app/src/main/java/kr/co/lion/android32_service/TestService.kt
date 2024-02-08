package kr.co.lion.android32_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

// 안드로이드 8.0 이상 부터는 서비스 가동시 알림 메시지를 띄워야 한다.
// 이때, AndroidManifest.xml에 FOREGROUND_SERVICE 권한을 등록해야 한다.

class TestService : Service() {

    // 쓰레드의 반복문의 조건식으로 사용할 변수
    var isRunning = false

    var value = 0

    // Activity가 서비스에 접속하면 Activity로 전달될 객체
    val binder = LocalBinder()

    // 외부에서 서비스에 접속하면 호출되는 메서드
    // 여기에서 Binder객체를 반환하면 Binder 객체를 Activity에서 받을 수 있다.
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    // 서비스가 가동되면 자동으로 호출되는 메서드
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // 안드로이드 8.0 부터는 알림 메시지를 띄워줘야 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 알림 채널 등록
            addNotificationChannel("Service", "Service")
            // 알림 메시지 구성
            val builder = getNotificationBuilder("Service")
            builder.setSmallIcon(android.R.drawable.ic_btn_speak_now)
            builder.setContentTitle("서비스 가동")
            builder.setContentText("서비스가 가동 중입니다")
            // 알림 메시지를 띄운다
            // 안드로이드 8.0 부터는 서비스 가동 시 알림 메시지를 띄워야 한다.
            // 안드로이드 10.0 에서 서비스에 대한 용도를 지정하는 개념이 추가되었다.
            // 예) startForeground(10,notification,ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
            // 용도를 지정하게 되면 구글에서 용도에 맞는지 확인하고 용도에 맞으면 검수를 통과시켜준다.
            // 이 때, 용도를 명시했고 구글로부터 인증을 받았기 때문에 알림 메시지를 띄우지 않는다.
            // 안드로이드 14버전 부터 서비스의 용도를 명시하는 것이 의무화 되었다.
            // 따라서 안드로이드 14버전으로 테스트 할때는 알림 메시지가 나타나지 않는다.
            // https://developer.android.com/about/versions/14/changes/fgs-types-required?hl=ko
            val notification = builder.build()
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE){
                startForeground(10,notification,ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
            }else{
                // 안드로이드 13까지는 용도를 명시하지 않음
                startForeground(10,notification)
            }
        }

        // 쓰레드를 가동한다.
        isRunning = true
        thread{
            while(isRunning){
                SystemClock.sleep(500)
                val now = System.currentTimeMillis()
                Log.d("test1234","현재시간 : $now")
                value++
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    // 서비스가 중지되면 호출되는 메서드
    override fun onDestroy() {
        super.onDestroy()

        // 쓰레드 중단을 위해 변수에 false를 넣어준다.
        isRunning = false
    }

    // Notification Channel 등록
    // 첫 번째 : 코드에서 채널을 관리하기 위한 이름
    // 두 번째 : 사용자에게 보여줄 채널의 이름
    fun addNotificationChannel(id:String, name:String){
        // 안드로이드 8.0 이상일 때만 동작하게 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 알림 메시지를 관라하는 객체를 가져온다.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // 해당 채널이 등록되어 있는지 확인한다.
            // 채널이 등록되어 있지 않으면 null을 반환한다.
            val channel = notificationManager.getNotificationChannel(id)
            // 등록된 채널이 없다면
            if(channel == null){
                // 채널 객체를 생성한다.
                val newChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                // 진동을 사용할 것인가.
                newChannel.enableVibration(true)
                // 채널을 등록한다.
                notificationManager.createNotificationChannel(newChannel)
            }
        }
    }

    // Notification 메새지를 생성하기 위한 객체를 반환하는 메서드
    fun getNotificationBuilder(id:String) : NotificationCompat.Builder{
        // 안드로이드 8.0 이상이면 마지막 매개변수에 채널 id를 설정해줘야 한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val builder = NotificationCompat.Builder(this, id)
            return builder
        }  else {
            val builder = NotificationCompat.Builder(this)
            return builder
        }
    }

    // 프로퍼티의 값을 반환하는 메서드
    fun getNumber():Int{
        return value
    }

    // Activity에서 서비스에 접속하고 서비스 객체를 반환 받기 위한 클래스
    inner class LocalBinder : Binder(){
        fun getService() : TestService{
            return this@TestService
        }
    }
}