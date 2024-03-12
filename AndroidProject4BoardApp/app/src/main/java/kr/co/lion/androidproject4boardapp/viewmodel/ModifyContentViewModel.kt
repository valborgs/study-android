package kr.co.lion.androidproject4boardapp.viewmodel

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import kr.co.lion.androidproject4boardapp.ContentType
import kr.co.lion.androidproject4boardapp.R

class ModifyContentViewModel : ViewModel() {
    // 글 제목
    val textFieldModifyContentSubject = MutableLiveData<String>()
    // 게시판 종류
    val toggleModifyContentType = MutableLiveData<Int>()
    // 글 내용
    val textFieldModifyContentText = MutableLiveData<String>()


    // 게시판 종류를 받아 MutableLiveData에 설정하는 메서드
    fun settingContentType(contentType: ContentType){
        toggleModifyContentType.value = when(contentType){
            ContentType.TYPE_ALL    -> 0
            ContentType.TYPE_FREE   -> R.id.buttonModifyContentType1
            ContentType.TYPE_HUMOR  -> R.id.buttonModifyContentType2
            ContentType.TYPE_SOCIETY-> R.id.buttonModifyContentType3
            ContentType.TYPE_SPORTS -> R.id.buttonModifyContentType4
        }
    }


    companion object {
        //====단방향==============================================================================
        // toggleAddContentType에 값을 설정했을 때 checkedButtonId 속성에 값을 반영해줄 때 호출(순방향)
        @BindingAdapter("android:checkedButtonId")
        @JvmStatic
        fun setCheckedButtonId(group: MaterialButtonToggleGroup, buttonId:Int){
            group.check(buttonId)
        }

        //====양방향==============================================================================

        // 안드로이드 OS가 현재 화면을 구성하고자 할 때 InverseBindingAdapter에 등록되어 있는 event 값을 보고
        // event에 등록되어 있는 이름과 동일한 BindingAdapter를 찾아 메서드를 호출해준다.
        // 리스너 설정을 해준다.
        @BindingAdapter("checkedButtonChangeListener")
        @JvmStatic
        fun checkedButtonChangeListener(group: MaterialButtonToggleGroup, inverseBindingListener: InverseBindingListener){
            group.addOnButtonCheckedListener { group, checkedId, isChecked ->
                inverseBindingListener.onChange()
            }
        }

        // 안드로이드 OS가 현재 화면을 구성하고자 할 때 속성에 설정된 MutableLiveData가 양방향(@=)으로 되어있을 경우
        // 참고할 데이터가 셋팅되어 있는 메서드
        // inverseBindingListener.onChange() 호출시 동작할 메서드
        @InverseBindingAdapter(attribute = "android:checkedButtonId", event = "checkedButtonChangeListener")
        @JvmStatic
        fun getCheckedButtonId(group: MaterialButtonToggleGroup):Int{
            return group.checkedButtonId
        }
    }
}