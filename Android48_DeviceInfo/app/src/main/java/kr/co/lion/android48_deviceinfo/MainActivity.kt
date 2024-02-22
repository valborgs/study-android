package kr.co.lion.android48_deviceinfo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import kr.co.lion.android48_deviceinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val permissionList = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_PHONE_NUMBERS,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        requestPermissions(permissionList,0)

        // 전화 관련된 정보를 관리하는 객체를 추출한다.
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        // 권한 확인이 필요한 일부 코드들은 권한 허용 여부를 검사해줘야 하는 것들이 있다.
        // checkSelfPermission : 두 번째 매개변수에 넣어준 권한이 허용되어 있는지
        // PERMISSION_GRANTED : 허용된 권한
        // PERMISSION_DENIED : 거부된 권한
        val a1 = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        val a2 = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        val a3 = ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED

        // 권한이 모두 허용되어 있다면
        if(a1 && a2 && a3){
            activityMainBinding.apply {
                textView.apply {
                    text = "전화번호 : ${telephonyManager.line1Number}\n"
                    append("SIM 국가 코드 : ${telephonyManager.simCountryIso}\n")
                    append("모바일 국가 코드 + 모바일 네트워크 코드 : ${telephonyManager.simOperator}\n") // 통신사에 따라 가능한 기능을 구현할때 체크
                    append("서비스 이름 : ${telephonyManager.simOperatorName}\n")
                    append("SIM 상태 (통신 가능 여부, Pin Lock 여부 등) : ${telephonyManager.simState}\n")
                    append("음성 메일 번호 : ${telephonyManager.voiceMailNumber}\n")

                    append("보드 이름 : ${Build.BOARD}\n")
                    append("소프트웨어를 커스터마이징한 회사 : ${Build.BRAND}\n")
                    append("제조사 디자인 명 : ${Build.DEVICE}\n")
                    append("사용자에게 표시되는 빌드 ID : ${Build.DISPLAY}\n")
                    append("빌드 고유 ID : ${Build.FINGERPRINT}\n")
                    append("ChangeList 번호 : ${Build.ID}\n")
                    append("제품/하드웨어 제조업체 : ${Build.MANUFACTURER}\n")
                    append("제품 모델명 : ${Build.MODEL}\n")
                    append("제품명 : ${Build.PRODUCT}\n")
                    append("빌드 구분 : ${Build.TAGS}\n")
                    append("빌드 타입 : ${Build.TYPE}\n")
                    append("안드로이드 버전 : ${Build.VERSION.RELEASE}\n")
                    append("안드로이드 버전 코드네임 : ${Build.VERSION.CODENAME}\n")
                    append("안드로이드 API 레벨 : ${Build.VERSION.SDK_INT}\n")

                    // 화면에 관련된 정보를 가지고 있는 객체를 추출한다.
                    val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

                    // 안드로이드 11 이상 여부로 분기한다.
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        // 단말기 해상도의 가로길이를 가져온다.
                        val w = windowManager.currentWindowMetrics.bounds.width()
                        // 단말기 해상도의 세로길이를 가져온다.
                        val h = windowManager.currentWindowMetrics.bounds.height()

                        textView.append("가로길이 : ${w}\n")
                        textView.append("세로길이 : ${h}\n")
                    } else {
                        // 해상도 정보를 담을 객체를 추출한다.
                        val point = Point()
                        // 해상도 정보를 담는다.
                        windowManager.defaultDisplay.getSize(point)
                        
                        textView.append("가로길이 : ${point.x}\n")
                        textView.append("세로길이 : ${point.y}\n")
                    }
                }
            }
        }



    }
}