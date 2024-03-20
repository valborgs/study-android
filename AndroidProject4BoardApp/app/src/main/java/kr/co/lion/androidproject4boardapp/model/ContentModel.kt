package kr.co.lion.androidproject4boardapp.model

data class ContentModel(
    var contentIdx:Int, var contentSubject:String, var contentType:Int,
    var contentText:String, var contentImage:String?, var contentWriterIdx:Int, var contentWriteDate:String,
    var contentState:Int
) {
    // 매개 변수가 없는 생성자
    constructor():this(0, "", 0, "", "", 0, "", 0)
}