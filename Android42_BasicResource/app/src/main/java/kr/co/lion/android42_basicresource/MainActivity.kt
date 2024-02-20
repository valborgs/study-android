package kr.co.lion.android42_basicresource

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android42_basicresource.databinding.ActivityMainBinding

// res/values 폴더
// 프로그램에서 사용되는 값들을 설정하는 파일이 들어있다.
// values 폴더는 파일 이름은 중요하지 않다. values 폴더내에 있는 xml 파일에
// 등록되어 있는 값들의 이름으로 resource id가 만들어진다.

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // res/values/strings.xml에 등록한 문자열을 가져온다.
                val str1 = resources.getString(R.string.str1)
                textView.text = "${str1}\n"

                // 문자열은 프로그램에서 굉장히 많이 사용하는 요소이기 때문에
                // Activity에서 등록된 문자열 리소스를 가져오는 메서드를 제공한다.
                val str2 = getString(R.string.str2)
                textView.append("${str2}\n")

                // 문자열을 설정할 수 있는 View들은 문자열 리소스 아이디를 지정할 수 있도록 제공된다.
                textView.setText(R.string.str1)
            }

            button2.setOnClickListener {
                // res/values/string.xml에 등록한 문자열을 가져온다.
                // str3은 %s, %d, %f 등의 출력 양식을 가지고 있는 문자열이다.
                // 각 포멧 문자에 바인딩 될 값을 지정해줘야 한다.
                val str1 = getString(R.string.str3)
                // % 부분에 바인딩될 값을 지정하여 문자열을 완성한다.
                val str2 = String.format(str1, "홍길동", 30)

                textView.text = str2
            }

            button3.setOnClickListener {
                // res/values/string.xml에 등록한 문자열 배열을 가져온다.
                val str4Array = resources.getStringArray(R.array.str4_array)
                textView.text = ""
                str4Array.forEach {
                    textView.append("${it}\n")
                }
                
            }

            button4.setOnClickListener {
                textView.text = "색상값 테스트"

                // 사전 정의되어 있는 색상값 사용
                textView.setTextColor(Color.RED)

                // RGB 지정 (R - Red, G - Green, B - Blue, 빛의 삼원색)
                val c1 = Color.rgb(227, 30, 89)
                textView.setTextColor(c1)

                // ARGB 지정 (A - alpha, R, G, B)
                val c2 = Color.argb(50, 227, 30, 89)
                textView.setTextColor(c2)

                // 리소스에 등록된 색상
                val c3 = getColor(R.color.color3)
                textView.setTextColor(c3)

                // 안드로이드에서 제공하는 색상들
                val c4 = getColor(android.R.color.system_on_secondary_dark)
                textView.setTextColor(c4)
            }

            button5.setOnClickListener {
                // res/values/dimen.xml에 정의한 크기 값
                // 단위
                // px : 픽셀의 개수. 실제로 사용하는 단위
                // dp : 160ppi 액정에서 1dp = 1px. 단말기의 ppi 수치에 따른 px 값을 계산해준다.
                // sp : 160ppi 액정에서 1sp = 1px. 단말기 설정에서 설정한 글자 크기에 반영된다.
                // pt : 1/72 인치. 인쇄물에 사용하는 단위.

                val px = resources.getDimension(R.dimen.px)
                val dp = resources.getDimension(R.dimen.dp)
                val sp = resources.getDimension(R.dimen.sp)
                val inch = resources.getDimension(R.dimen.inch)
                val mm = resources.getDimension(R.dimen.mm)
                val pt = resources.getDimension(R.dimen.pt)

                textView.text = "1px = ${px}px\n"
                textView.append("1dp = ${dp}px\n")
                textView.append("1sp = ${sp}px\n")
                textView.append("1inch = ${inch}px\n")
                textView.append("1mm = ${mm}px\n")
                textView.append("1pt = ${pt}px\n")
            }
        }
    }
}