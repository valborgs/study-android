package kr.co.lion.android36_sqlitedatabase

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android36_sqlitedatabase.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 데이터 베이스 : 데이터들을 저장하고 관리하는 데이터 집합
// SQLite : 데이터 베이스를 관리하는 관계형 데이터 베이스 시스템(RDBMS)의 한 종류
// 테이블 : 데이터베이스 내에서 데이터를 묶어서 관리하는 요소
// 컬럼 : 테이블 내의 데이터 항목(컬럼, 열)
// 로우 : 테이블에 저장되어 있는 개체 하나에 대한 컬럼의 묶음(로우, 행)

// 안드로이드에서 SQLite Database 사용
// 1. SQLiteOpenHelper 를 상속받은 클래스를 만들어준다(DBHelper.kt)


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

//        // SQLiteOpenHelper 객체 생성
//        val dbHelper = DBHelper(this, "TEST.db", DBHelper.databaseVersion)
//        // 데이터 베이스에 접속
//        // 이때 데이터 베이스 파일을 찾게 된다. 파일이 있으면 데이터 베이스 파일을 열고
//        // 없으면 파일을 생성하고 데이터 베이스 파일을 열고 SQLiteOpenHelper 에 있는 onCreate 메서드를 호출한다.
//        // 만약 기존 데이터 베이스 파일 버전보다 더 높은 버전으로 설정하면 onUpgrade 가 호출된다.
//        val sqLiteDatabase = dbHelper.writableDatabase
//
//        // 데이터 베이스를 닫아준다.
//        sqLiteDatabase.close()

        activityMainBinding.apply {
            // 저장(쿼리문)
            buttonInsert1.setOnClickListener {
                // 쿼리문
                // autoincrement를 설정한 컬럼은 제외한다.
                // insert into 테이블명 (컬럼명, 컬럼명, 컬럼명, ...) values(값, 값, 값, ...)
                // 값이 들어가는 부분은 ?로 설정해준다.
                val sql = """insert into TestTable
                    (data1, data2, data3, data4)
                    values(?, ?, ?, ?)
                """.trimIndent()

                // 현재 시간을 구해 년-월-일 양식의 문자열로 만들어준다.
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val now = sdf.format(Date())

                // ?에 맵핑될 값
                // ?에 맵핑될 값을 순서대로 작성해줘야 한다.
                val arg1 = arrayOf(100, 11.11, "문자열1",now)
                val arg2 = arrayOf(200, 22.22, "문자열2",now)

                // 데이터 베이스를 사용한다.
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)

                // 쿼리문을 실행한다.
                // 첫 번째 - 쿼리문
                // 두 번째 - ?에 바인딩될 값이 담긴 배열
                dbHelper.writableDatabase.execSQL(sql, arg1)
                dbHelper.writableDatabase.execSQL(sql, arg2)

                // 데이터 베이스를 닫아준다.
                dbHelper.close()

                textViewResult.text = "데이터가 저장되었습니다 (쿼리문)"
            }

            // 저장(라이브러리)
            buttonInsert2.setOnClickListener {
                // 현재 시간을 구해 년-월-일 양식의 문자열로 만들어준다.
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val now = sdf.format(Date())

                // 데이터를 담을 객체
                val cv1 = ContentValues()
                // ContentValues는 이름을 통해 데이터를 보관하는 객체
                // 데이터를 저장할 때 사용하는 이름을 컬럼의 이름으로 지정해주면
                // 저장처리가 이루어진다.
                cv1.put("data1", 300)
                cv1.put("data2", 33.33)
                cv1.put("data3", "문자열3")
                cv1.put("data4", now)
                // autoincrement가 있는 컬럼은 지정해주지 않아도 된다.

                val cv2 = ContentValues()
                cv2.put("data1", 400)
                cv2.put("data2", 44.44)
                cv2.put("data3", "문자열4")
                cv2.put("data4", now)

                // 저장한다.
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)

                // 첫 번째 : 데이터를 저장할 테이블의 이름
                // 두 번째 : null 값을 어떻게 처리할 것인가.. 그냥 null 지정해주세요....
                // 세 번째 : 저장할 데이터를 가지고 있는 ContentValues
                dbHelper.writableDatabase.insert("TestTable", null, cv1)
                dbHelper.writableDatabase.insert("TestTable", null, cv2)

                dbHelper.close()

                textViewResult.text = "데이터가 저장되었습니다 (라이브러리)"
            }

            // 데이터 가져오기(쿼리문)
            buttonSelect1.setOnClickListener {
                // 쿼리문
                // select (컬럼명, 컬럼명, ...) 또는 * from 테이블명 where 조건식
                val sql = """select idx, data1, data2, data3, data4
                    from TestTable
                """.trimIndent()

                // 쿼리 실행
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)
                // rawQuery() 쿼리문을 통해 가져온 객체를 받아옴
                // 첫 번째 : 쿼리문
                // 두 번째 : ? 부분이 있을 경우 ? 부분에 맵핑될 값 배열, 없으면 null
                val cursor = dbHelper.writableDatabase.rawQuery(sql,null)

                showResult(cursor)

                dbHelper.close()

            }

            // 데이터 가져오기(라이브러리)
            buttonSelect2.setOnClickListener {
                // 가져올 컬럼 목록
                // null을 담으면 select * 과 같이 모든 컬럼을 불러올 수 있음
                val cols = arrayOf("idx","data1","data2","data3","data4")
                // 테이블 명
                val tableName = "TestTable"

                // 데이터를 가져온다.
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)

                // 첫 번째 : 테이블명
                // 두 번째 : 가지고 오고자 하는 컬럼 목록. null을 넣어주면 모두 가져온다.
                // 세 번째 : 특정 행을 선택하기 위한 조건절
                // 네 번째 : 조건절의 ? 에 들어갈 값 배열
                // 다섯 번째 : Group by 의 기준 컬럼
                // 여섯 번째 : Having 절에 들어갈 조건절
                // 일곱 번째 : Having 절의 ?에 들어갈 값 배열
                val cursor = dbHelper.writableDatabase.query(tableName, cols, null, null, null, null, null)

                showResult(cursor)

                dbHelper.close()
            }

            // 수정(쿼리)
            buttonUpdate1.setOnClickListener {
                // 쿼리문
                // Update 테이블명
                // set 컬럼=값, 컬럼=값, 컬럼=값 ...
                // where 조건절
                val sql = """update TestTable 
                    set data1=?, data2=?
                    where idx=?
                """.trimIndent()

                // ?에 들어갈 값
                val args1 = arrayOf(1000, 111.111, 1)
                val args2 = arrayOf(2000, 222.222, 2)

                // 쿼리 실행
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)
                dbHelper.writableDatabase.execSQL(sql,args1)
                dbHelper.writableDatabase.execSQL(sql,args2)
                dbHelper.close()

                textViewResult.text = "수정되었습니다 (쿼리)"
            }

            // 수정 (라이브러리)
            buttonUpdate2.setOnClickListener {
                // ContentValues에 컬럼명과 값을 담아준다
                val cv1 = ContentValues()
                cv1.put("data1", 3000)
                cv1.put("data2", 333.333)

                val cv2 = ContentValues()
                cv2.put("data1", 4000)
                cv2.put("data2", 444.444)

                // 조건절
                val condition = "idx = ?"
                // 조건절 ? 에 바인딩 될 값 배열
                // 모든 값들은 반드시 문자열로 되어 있어야 한다.
                val arg1 = arrayOf("3")
                val arg2 = arrayOf("4")

                // 쿼리 실행
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)
                dbHelper.writableDatabase.update("TestTable", cv1, condition, arg1)
                dbHelper.writableDatabase.update("TestTable", cv2, condition, arg2)
                dbHelper.close()

                textViewResult.text = "수정되었습니다 (라이브러리)"
            }
            
            // 삭제 (쿼리)
            buttonDelete1.setOnClickListener { 
                // 쿼리문
                // delete from 테이블명
                // where 조건절
                val sql = """delete from TestTable
                    where idx = ?
                """.trimIndent()
                // ?에 들어갈 값
                val args = arrayOf(1)

                // 쿼리 실행
                val dbHelper = DBHelper(this@MainActivity, "Test.db", DBHelper.databaseVersion)
                dbHelper.writableDatabase.execSQL(sql, args)
                dbHelper.close()

                textViewResult.text = "삭제되었습니다 (쿼리)"
            }

            // 삭제 (라이브러리)
            buttonDelete2.setOnClickListener {
                // 테이블명
                val tableName = "TestTable"
                // 조건절
                val condition = "idx = ?"
                // 조건절 ?에 들어갈 값. 문자열로 해야 한다.
                val args = arrayOf("2")

                val dbHelper = DBHelper(this@MainActivity,"Test.db",DBHelper.databaseVersion)
                dbHelper.writableDatabase.delete(tableName,condition,args)
                dbHelper.close()

                textViewResult.text = "삭제되었습니다 (라이브러리)"
            }

        }

    }

    // 가져온 데이터를 출력하는 메서드
    fun showResult(cursor:Cursor){

        activityMainBinding.textViewResult.text = ""

        // moveToNext : Cursor 객체한테 다음 행에 접근하라고 명령하는 메서드
        // 접근에 성공하면 true를 반환하고 접근에 실패(더이상 행이 없는 경우)하면 false를 반환한다.
        // 처음 실행하는 경우에는 첫번째 행에 접근한다.
        // 가져온 행이 1개밖에 없어도 moveToNext를 꼭 실행해준다.
        while(cursor.moveToNext()){
            // 컬럼 이름을 지정하여 가져온 결과에서 몇 번째에 있는지에 대한 순서값을 가지고 온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("data1")
            val idx3 = cursor.getColumnIndex("data2")
            val idx4 = cursor.getColumnIndex("data3")
            val idx5 = cursor.getColumnIndex("data4")

            // 위에서 구한 순서값을 통해 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val data1 = cursor.getInt(idx2)
            val data2 = cursor.getDouble(idx3)
            val data3 = cursor.getString(idx4)
            val data4 = cursor.getString(idx5)

            // 출력한다.
            activityMainBinding.apply {
                textViewResult.apply {
                    append("idx : ${idx}\n")
                    append("data1 : ${data1}\n")
                    append("data2 : ${data2}\n")
                    append("data3 : ${data3}\n")
                    append("data4 : ${data4}\n\n")
                }
            }
        }
    }
}