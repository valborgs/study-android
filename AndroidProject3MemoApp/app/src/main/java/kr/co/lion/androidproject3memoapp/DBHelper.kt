package kr.co.lion.androidproject3memoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "MemoDB.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        // 테이블을 만드는 쿼리문
        val sql = """
            create table MemoTable(
            memoIdx integer primary key autoincrement,
            memoSubject text not null,
            memoDate date not null,
            memoText text not null)
        """.trimIndent()

        // 쿼리 실행
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}