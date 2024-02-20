package kr.co.lion.android41_preferencesscreeen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat

// build.gradle 에 androidx.preference:perference 라이브러리를 추가한다.
class SettingFragment : PreferenceFragmentCompat() {
    // rootKey : Preference의 이름
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // xml 폴더에 있는 preference xml 파일을 지정해준다.
        setPreferencesFromResource(R.xml.pref, rootKey)
    }
}

// pref.xml 파일 내용 설명

// PreferenceCategory
// Preference 요소들을 그룹으로 묶어주는 역할을 한다.
// 특별한 기능은 없고 화면이 구성될 때 그룹을 묶어서 보여주는 역할을 수행한다.
// title : 그룹에 표시할 타이틀 문자열

// EditTextPreference : 문자열 입력
// defaultValue : 초기값(String)
// title : 화면에 보여지는 이름
// key : 데이터를 가져올 때 사용하는 이름
// summary : 표시하고자 하는 설명
// icon : 좌측에 표시될 아이콘
// dialogIcon : 입력을 위해 뜨는 다이얼로그의 아이콘
// dialogTitle : 입력을 위해 뜨는 다이얼로그의 타이틀
// dialogMessage : 입력을 위해 뜨는 다이얼로그의 메시지

// CheckBoxPreference : 체크박스
// defaultValue : 초기값(Boolean)
// key : 데이터를 가져올 때 사용하는 이름
// title : 화면에 보여지는 이름
// summary : 표시되는 설명
// icon : 좌측에 표시되는 아이콘
// summary : 표시하고자 하는 설명
// summaryOff : 체크가 해제 되어 있을 때 보여줄 설명 문자열. 설정되어 있지 않으면 summary 문자열을 보여준다.
// summaryOn : 체크되어 있을 때 보여줄 설명 문자열. 설정되어 있지 않으면 summary 문자열을 보여준다.

// SwitchPreference : 스위치
// defaultValue : 초기값(Boolean)
// key : 데이터를 가져올 때 사용하는 이름
// title : 화면에 보여지는 이름
// summary : 표시되는 설명
// icon : 좌측에 표시되는 아이콘
// summary : 표시하고자 하는 설명
// summaryOff : off 상태일 때 보여줄 설명 문자열. 설정되어 있지 않으면 summary 문자열을 보여준다.
// summaryOn : on 상태일 때 보여줄 설명 문자열. 설정되어 있지 않으면 summary 문자열을 보여준다.

// ListPreference : 항목들을 보여주고 그 중에 하나를 선택할 수 있게 한다.
// defaultValue : 초기값(String)
// key : 데이터를 가져올 때 사용하는 이름
// title : 화면에 보여지는 이름
// summary : 표시되는 설명
// icon : 좌측에 표시되는 아이콘
// dialogIcon : 다이얼로그의 아이콘
// entries : 화면상에 보여줄 항목의 문자열
// entryValues : 코드에서 사용하는 값

// MultiSelectListPreference : 항목들을 보여주고 다수를 선택할 수 있다.
// defaultValue : 초기값, 반드시 entryValues에 설정되어 있는 문자열 중에 원하는 것들을 모두 설정해준다.
// key : 데이터를 가져올 때 사용하는 이름
// title : 화면에 보여지는 이름
// summary : 표시되는 설명
// icon : 좌측에 표시되는 아이콘
// dialogIcon : 다이얼로그의 아이콘
// entries : 화면상에 보여줄 항목의 문자열
// entryValues : 코드에서 사용하는 값