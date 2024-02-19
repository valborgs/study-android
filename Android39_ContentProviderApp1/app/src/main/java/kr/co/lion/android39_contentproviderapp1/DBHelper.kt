package kr.co.lion.android39_contentproviderapp1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Test.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """create table TestTable
            | (idx integer primary key autoincrement,
            |  data1 integer not null,
            |  data2 real not null,
            |  data3 text not null,
            |  data4 date not null)
        """.trimMargin()

        // 쿼리문을 실행한다.
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}