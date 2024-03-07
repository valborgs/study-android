package kr.co.lion.android53_mvvm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    // View에 설정할 값을 담을 객체
    // 제네릭은 반드시 View의 속성에 대한 값의 타입으로 맞춰줘야 한다. (예를들어 text속성은 String타입)
    val edit1Data = MutableLiveData<String>()
    val edit2Data = MutableLiveData<String>()
    val textViewData = MutableLiveData<String>("test")

    // 버튼을 누르면 동작하는 메서드
    fun buttonClick(view: View){
        // 입력한 값을 가져온다.
        val data1 = edit1Data.value?.toInt()!!
        val data2 = edit2Data.value?.toInt()!!

        val r1 = data1 + data2

        // 출력한다
        textViewData.value = r1.toString()
    }
}