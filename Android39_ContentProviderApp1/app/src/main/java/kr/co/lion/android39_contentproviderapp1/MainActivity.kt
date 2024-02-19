package kr.co.lion.android39_contentproviderapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android39_contentproviderapp1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                // 쿼리문
                val sql = """
                        select idx, data1, data2, data3, data4
                        from TestTable
                    """.trimIndent()

                // 쿼리 실행
                val dbHelper = DBHelper(this@MainActivity)
                val cursor = dbHelper.writableDatabase.rawQuery(sql, null)

                while(cursor.moveToNext()){
                    // 컬럼 순서값을 가져온다.
                    val idx1 = cursor.getColumnIndex("idx")
                    val idx2 = cursor.getColumnIndex("data1")
                    val idx3 = cursor.getColumnIndex("data2")
                    val idx4 = cursor.getColumnIndex("data3")
                    val idx5 = cursor.getColumnIndex("data4")

                    // 데이터를 가져온다.
                    val  idx = cursor.getInt(idx1)
                    val  data1 = cursor.getInt(idx2)
                    val  data2 = cursor.getDouble(idx3)
                    val  data3 = cursor.getString(idx4)
                    val  data4 = cursor.getString(idx5)

                    textView.apply {
                        text = "idx : ${idx}\n"
                        append("data1 : ${data1}\n")
                        append("data2 : ${data2}\n")
                        append("data3 : ${data3}\n")
                        append("data4 : ${data4}\n")
                    }

                }
                dbHelper.close()
            }
        }

    }
}