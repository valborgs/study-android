package kr.co.lion.ex12_sqlitedatabase1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Test.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Int -> integer
        // Double -> real
        // String -> text
        // 날짜 -> date
        val sql = """create table StudentTable
            (idx integer primary key autoincrement,
             name text not null,
             grade integer not null,
             kor integer not null,
             eng integer not null,
             math integer not null)
        """.trimIndent()

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}