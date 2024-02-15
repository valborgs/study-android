package kr.co.lion.android36_sqlitedatabase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// SQLiteOpenHelper 생성자의 매개변수
// 첫 번째 : 컨텍스트
// 두 번째 : 데이터 베이스 파일의 이름. 이 이름으로 만들어진 데이터 베이스 파일이 없으면 OS가 파일을 만들어주고
//          OS가 onCreate 메서드를 호출한다.
// 세 번째 : null에 대한 처리를 어떻게 할 것인가. 그냥 null로 셋팅해준다.
// 네 번째 : 데이터 베이스 파일의 버전. 애플리케이션 버전과 무관하며 아무 숫자나 지정해줘도 된다.
//          애플리케이션을 서비스 하는 중에 테이블의 개수나 구조를 변경해야 하는 경우
//          이전의 숫자보다 높은 숫자로 지정해주면 예전에 저장한 애플리케이션들은 onUpgrade를 호출해준다.

class DBHelper(context: Context, dbName:String, version:Int) : SQLiteOpenHelper(context, dbName, null, version) {

    companion object{
        val databaseVersion = 2
    }

    // 사용하고자 하는 데이터 베이스 파일이 없을 경우 파일을 만들어주고 호출되는 메서드
    // 테이블을 생성하는 작업을 수행한다.

    // 테이블을 만드는 쿼리문
    // create table 테이블이름 (컬럼이름, 자료형 제약조건, 컬럼이름 자료형 제약조건, ...)
    // 자료형 : 정수 - integer, 문자열 - text, 실수 - real, 날짜 - date
    // 제약조건 : 저장할 수 있는 값에 대한 조건
    // primary key : null을 허용하지 않고 중복된 값을 허용하지 않는다.
    // 각 행들을 개별적으로 구분하기위한 값을 저장하기 위해 사용한다.
    // autoincrement : 컬럼에 저장할 값을 지정하지 않으면 1부터 1씩 증가되는 값이 자동으로 저장된다.
    // not null : null을 허용하지 않는다. 개발자가 무조건 값을 정해줘야 한다.

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """create table TestTable
            (idx integer primary key autoincrement,
             data1 integer not null,
             data2 real not null,
             data3 text not null,
             data4 date not null)
        """.trimIndent()

        // 쿼리문을 실행한다.
        db?.execSQL(sql)
    }

    // 데이터 베이스 파일의 버전이 올라갔을 경우 호출되는 메서드
    // 테이블의 구조를 최종 형태로 변경하는 작업을 수행한다.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("test1234", "onUpgrade : $oldVersion -> $newVersion")
    }

}