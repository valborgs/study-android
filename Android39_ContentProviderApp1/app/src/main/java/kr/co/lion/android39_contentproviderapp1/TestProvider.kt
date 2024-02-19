package kr.co.lion.android39_contentproviderapp1

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class TestProvider : ContentProvider() {

    // 데이터 베이스 접근 객체
    lateinit var sqliteDatabase:SQLiteDatabase

    // 삭제
    // 두번째 : 조건절
    // 세번째 : 조건절의 ?에 바인딩될 값
    // 반환값 : 삭제된 행의 개수
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val cnt = sqliteDatabase.delete("TestTable",selection,selectionArgs)
        return cnt
    }

    // ContentProvider의 authorities를 반환해준다.
    // ContentProvider 사용하는 쪽에서 다수의 Provider를 사용하고 있다면
    // 이를 구분하기 위한 용도로 사용한다.
    override fun getType(uri: Uri): String? {
        return uri.authority
    }

    // 첫번째 : authorities가 담긴 uri 객체
    // 두번째 : 저장할 데이터가 담긴 객체
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // 저장한다.
        sqliteDatabase.insert("TestTable", null, values)

        return uri
    }

    // ContentProvider가 생성될 때 자동으로 호출된다.
    // 별로 할 작업이 없다.
    // 데이터베이스에 접속하는 작업을 한다.
    override fun onCreate(): Boolean {

        val dbHelper = DBHelper(context!!)
        sqliteDatabase = dbHelper.writableDatabase

        return true
    }

    // select
    // 첫 번째 : authorities 가 담긴 Uri 객체
    // 두 번째 : 가져올 컬럼의 목록
    // 세 번째 : 조건 절
    // 네 번째 : 조건 절의 ?에 들어갈 값
    // 다 섯번째 : 정렬 기준
    // 반환 : 데이터를 접근할 수 있는 커서 객체
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor = sqliteDatabase.query("TestTable", projection, selection, selectionArgs, null, null, sortOrder)
        // contentprovider가 소멸될때 OS에서 자동으로 열린 db객체를 닫아주기 때문에
        // 여기서 close할필요가 없음
        // sqliteDatabase.close()

        return cursor
    }

    // update
    // 두 번째 : 저장할 데이터
    // 세 번째 : 조건절
    // 네 번째 : 조건절의 ?에 바인딩될 값 배열
    // 반환 : 수정이 적용된 행의 개수
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val cnt = sqliteDatabase.update("TestTable", values, selection, selectionArgs)

        return cnt
    }
}