package kr.co.lion.android53_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import kr.co.lion.android53_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)

        testViewModel = TestViewModel()
        activityMainBinding.testViewModel = testViewModel
        // 데이터 바인딩 객체의 수명 설정
        // 액티비티가 소멸되면 바인딩 객체도 소멸된다.
        activityMainBinding.lifecycleOwner = this
        
        setContentView(activityMainBinding.root)
    }


}