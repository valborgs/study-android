package kr.co.lion.android10_logcat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

// 로그캣 : 안드로이드 애플리케이션 개발 시 콘솔 출력용으로 사용하는 도구
// 안드로이드 스튜디오에 있는 LogCat 창을 통해 확인할 수 있다.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 총 6가지 있다.
        // 어떤 걸 쓰더라도 주어진 메세지를 출력하는 것은 동일하다.
        // 메서드에 따라서 출력되는 색상이 달라진다.
        // 용도에 맞게 사용하는 것을 권장한다.

        // d : debug, 주로 개발자가 출력하고 싶은 메시지를 출력하는데 사용한다.
        // 다른 메서드에 해당하지 않는 것들을 출력할 때 사용한다.
        Log.d("test1234","debug")
        // e : error, 오류에 관련된 메시지를 출력하는데 사용한다.
        // 애플리케이션에서 오류가 발생하면 오류 메시지는 그냥 출력된다.
        Log.e("test1234","error")
        // i : information, 정보성 메시지를 출력하는데 사용한다.
        Log.i("test1234", "information")
        // v : verbose, 상세한 설명 메시지를 출력하는데 사용한다.
        Log.v("test1234", "verbose")
        // w : warning, 경고성 메시지를 출력하는데 사용한다.
        Log.w("test1234","warning")
        // wtf : What a Terriable Failure, 절대로 일어나서는 안되는 일이 벌어졌을때의
        // 메시지를 출력하는데 사용한다.
        Log.wtf("test1234","wtf")
    }
}