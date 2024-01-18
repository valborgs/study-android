package kr.co.lion.Android01_HelloWorld

// 폴더 구조
// manifests/AndroidManifest.xml
// 개발자가 안드로이드 OS에게 알려주고 싶은 애플리케이션의 정보를
// 기록하는 파일
// kotlin+java : 코틀린 코드를 작성하는 곳
// res : 애플리케이션에서 사용하는 각종 이미지, 사운드, 데이터, 영상 등등의
// 리소스를 넣어두는 곳

// 애플리케이션을 단말기에 설치하면...
// 1. 안드로이드 os는 애플리케이션이 가지고 있는 AndroidManifest.xml 파일을
// 열어서 작성된 내용을 확인한다.
// 2. 이 파일에 작성되어 있는 내용을 토대로 애플리케이션을 관리한다.
// 3. 확인한 클래스의 객체를 생성한 다음 onCreate 메서드를 호출한다.
// class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    // Activity 객체가 생성되고 자동으로 호출되는 메서드
    // 이 메서드가 Activity 안에서 코드의 시작점이 된다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Activity가 관리할 화면을 만든다.
        // 매개변수에는 화면을 만들기 위해서 필요한 정보가 담긴
        // xml 파일을 설정한다.
        // R -> res 폴더
        // layout -> res 폴더 안에 있는 layout 폴더
        // activity_main -> layout 폴더에 있는 activity_main.xml
        setContentView(R.layout.activity_main)
    }
}