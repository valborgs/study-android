package kr.co.lion.android15_imageview

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.toDrawable
import kr.co.lion.android15_imageview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // 웹서버가 제공하는 이미지를 보여주고자 할 때
                // imageView2.setImageURI(사이트의 주소를 관리하는 URI 객체)
                // 프로젝트의 res/drawable, res/maipmap 폴더에 있는 이미지를
                // 보여주고자 할 때
                // imageView2.setImageResource(R.drawable.img_android)

                // Bitmap : Bitmap 타입의 데이터를 관리하는 이미지 객체
                // Bitmap 데이터 타입 : 각 픽셀의 색상값이 저장되어 있는 형태
                // Drawable : Bitmap 타입의 데이터를 관리하는 이미지 객체
                // Drawable 은 이미지 데이터에 대한 변경작업이 가능한 이미지 객체
                // Bitmap이나 Drawable 객체로 만들어 쓰는 이미지는 res 폴더 내부가 아닌 곳에 존재하는
                // 이미지를 가져다 사용할 때 이 타입의 객체로 만들어 사용한다.
                // Bitmap과 Drawable 둘 다 제공하는 작업인 경우 Bitmap을 추천한다.
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_android)
                // imageView2.setImageBitmap(bitmap)

                val drawable = bitmap.toDrawable(resources)
                imageView2.setImageDrawable(drawable)
            }
        }
    }
}