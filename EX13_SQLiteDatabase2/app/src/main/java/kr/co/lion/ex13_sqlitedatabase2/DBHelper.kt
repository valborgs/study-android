package kr.co.lion.ex13_sqlitedatabase2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Student.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Int -> integer
        // Double -> real
        // String -> text
        // 날짜 -> date
        val sql = """create table StudentTable
            (idx integer primary key autoincrement,
             name text not null,
             age integer not null,
             kor integer not null)
        """.trimIndent()

        db?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}