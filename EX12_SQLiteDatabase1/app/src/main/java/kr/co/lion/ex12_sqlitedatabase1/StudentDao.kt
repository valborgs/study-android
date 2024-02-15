package kr.co.lion.ex12_sqlitedatabase1

import android.content.Context

class StudentDao {

    companion object{

        // select one
        fun selectOneStudent(context: Context, idx:Int) : StudentModel{
            // 쿼리
            val sql = """select idx, name, grade, kor, eng, math
                | from StudentTable
                | where idx = ?
            """.trimMargin()

            // ?에 들어갈 값
            val args = arrayOf("$idx")

            // 쿼리 실행
            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql, args)

            // 데이터를 가져온다.
            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("name")
            val idx3 = cursor.getColumnIndex("grade")
            val idx4 = cursor.getColumnIndex("kor")
            val idx5 = cursor.getColumnIndex("eng")
            val idx6 = cursor.getColumnIndex("math")

            val idx = cursor.getInt(idx1)
            val name = cursor.getString(idx2)
            val grade = cursor.getInt(idx3)
            val kor = cursor.getInt(idx4)
            val eng = cursor.getInt(idx5)
            val math = cursor.getInt(idx6)

            // 객체에 데이터를 담는다.
            val studentModel = StudentModel(idx,name,grade,kor,eng,math)

            // 데이터 베이스를 닫아준다.
            dbHelper.close()

            return studentModel
        }

        // select all
        fun selectAllStudent(context:Context) : MutableList<StudentModel>{
            // 쿼리문
            // order by : 정렬
            // order by 기준컬럼 정렬방식
            // 정렬방식 : asc나 생략 - 오름차순, desc - 내림차순
            val sql = """select idx, name, grade, kor, eng, math
                from StudentTable
                order by idx desc
            """.trimIndent()

            // 쿼리 실행
            val dbHelper = DBHelper(context)
            val cursor = dbHelper.writableDatabase.rawQuery(sql,null)

            // 데이터를 담을 리스트
            val studentList = mutableListOf<StudentModel>()

            // 데이터를 가져온다.
            while (cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("name")
                val idx3 = cursor.getColumnIndex("grade")
                val idx4 = cursor.getColumnIndex("kor")
                val idx5 = cursor.getColumnIndex("eng")
                val idx6 = cursor.getColumnIndex("math")

                val idx = cursor.getInt(idx1)
                val name = cursor.getString(idx2)
                val grade = cursor.getInt(idx3)
                val kor = cursor.getInt(idx4)
                val eng = cursor.getInt(idx5)
                val math = cursor.getInt(idx6)

                // 객체에 데이터를 담는다.
                val studentModel = StudentModel(idx,name,grade,kor,eng,math)

                // 객체를 리스트에 담는다.
                studentList.add(studentModel)
            }

            // 데이터 베이스를 닫아준다.
            dbHelper.close()

            return studentList
        }

        // insert
        fun insertStudent(context: Context, studentModel: StudentModel){
            // 쿼리문
            val sql = """insert into StudentTable
                (name, grade, kor, eng, math)
                values(?, ?, ?, ?, ?)
            """.trimIndent()

            // ?에 바인딩될 값
            val args = arrayOf(
                studentModel.name,
                studentModel.grade,
                studentModel.kor,
                studentModel.eng,
                studentModel.math
            )

            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // update
        fun updateStudent(context: Context, studentModel: StudentModel){
            // 쿼리문
            val sql = """update StudentTable
                set name=?, grade=?, kor=?, eng=?, math=?
                where idx=?
            """.trimIndent()

            // ?에 바인딩될 값
            val args = arrayOf(
                studentModel.name,
                studentModel.grade,
                studentModel.kor,
                studentModel.eng,
                studentModel.math,
                studentModel.idx,
            )

            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }

        // delete
        fun deleteStudent(context: Context, idx: Int){
            // 쿼리문
            val sql = """delete from StudentTable
                where idx = ?
            """.trimIndent()

            // ?에 바인딩될 값
            val args = arrayOf(idx)

            // 쿼리 실행
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.execSQL(sql, args)
            dbHelper.close()
        }
    }

}