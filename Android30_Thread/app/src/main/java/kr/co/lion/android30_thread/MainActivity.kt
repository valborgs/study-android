package kr.co.lion.android30_thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kr.co.lion.android30_thread.databinding.ActivityMainBinding
import kotlin.concurrent.thread

// Thread : 작업의 단위
// Thread 하나당 하나의 작업을 처리할 수 있다.
// 안드로이드 4대 구성요소 (Activity, Service, BR, CP)가 실행되면
// 그 안에 작성된 작업을 처리하기 위해 안드로이드 OS가 쓰래드를 발생시킨다.
// 만약 다른 작업을 동시에 처리하고자 한다면 Thread를 발생시켜 주면 된다.
// Thread에서 처리하는 작업에 오류가 발생하면 Thread 가 중단된다.
// Thread는 동시에 처리하고자 하는 작업이 있거나
// 오류가 발생할 가능성이 있는 작업을 처리할 때 사용한다.

// UI Thread, original Thread : 안드로이드 OS가 발생시킨 쓰래드. 화면 갱신, 생명주기와 관련된 메서드 호출, 리스너 처리 등의 일을
// 담당하게 된다. 이 쓰래드가 호출하는 메서드 내부의 코드는 무조건 금방 끝나야 한다.
// 사용자 쓰래드 : 개발자가 thread 코드 블럭을 이용해 발생시키는 쓰래드. 오래 걸리는 작업이나, 오류가 발생될
// 가능성이 있는 작업을 담당하게 한다. 네트워크, 지속적으로 반복해야 하는 작업

// runOnUiThread 코드 블럭 : 사용자 쓰래드 안에서 화면에 관련된 작업이 필요할 경우 화면과 관련된 코드를
// runOnUiThread 코드 블럭에 작성해 놓으면 UI Thread가 한가할 때 이 작업을 처리해준다.

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 사용자 쓰래드는 Activity가 종료되었다고 하더라도 계속 동작을 한다.
    // OS가 판단했을 때 시스템 메모리가 부족한 경우 사용자 쓰래드들이 모두 종료된다.(이를 방지하기 위해서는 Service를 사용해야 한다)
    // 따라서 Activity가 종료될 때 발생시킨 쓰래드들도 종료되게 해야 한다.
    // Thread 중지 여부를 처리할 변수
    var threadRunFlag1 = false
    var threadRunFlag2 = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 쓰래드를 발생시킨다.
        thread {

            threadRunFlag1 = true

            while(threadRunFlag1){
                SystemClock.sleep(100)
                val now = System.currentTimeMillis()
                Log.d("test1234", "Thread1 - $now")
                // 화면과 관련된 작업을 UI Thread가 처리할 수 있도록 한다.
                runOnUiThread {
                    activityMainBinding.textView.text = "Thread1 - $now"
                }
            }
        }

        thread{

            threadRunFlag2 = true

            while(threadRunFlag2){
                SystemClock.sleep(100)
                val now = System.currentTimeMillis()
                Log.d("test1234", "Thread2 - $now")
                runOnUiThread {
                    activityMainBinding.textView2.text = "Thread2 - $now"
                }
            }
        }

        activityMainBinding.apply {
            button.setOnClickListener {
                val now = System.currentTimeMillis()
                textView3.text = "버튼 클릭 : $now"
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // Activity가 종료될 때 쓰레드 내부의 코드가 종료될 수 있도록
        // while문 조건식에 있는 변수의 값을 false로 설정해준다.
        threadRunFlag1 = false
        threadRunFlag2 = false

    }
}