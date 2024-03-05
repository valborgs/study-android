package kr.co.lion.androidproject4boardapp

class Tools {

    companion object {

    }

}

// MainActivity에서 보여줄 프래그먼트들의 이름
enum class MainFragmentName(var str:String){
    LOGIN_FRAGMENT("loginFragment"),
    JOIN_FRAGMENT("joinFragment"),
    ADD_USER_INFO_FRAGMENT("addUserInfoFragment"),
}

// ContentActivity의 Fragments
enum class ContentFragmentName(var str:String){
    MAIN_FRAGMENT("mainFragment"),
    ADD_CONTENT_FRAGMENT("addContentFragment"),
    READ_CONTENT_FRAGMENT("readContentFragment"),
    MODIFY_CONTENT_FRAGMENT("modifyContentFragment"),
    MODIFY_USER_FRAGMENT("modifyUserFragment"),
}

// 체크박스 상태값
enum class CheckboxState{
    ALL_CHECKED,
    NOT_ALL_CHECKED,
    UNCHECKED
}