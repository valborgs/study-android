package kr.co.lion.ex10_project2

import android.os.Parcel
import android.os.Parcelable

class StudentData(var name:String?, var grade:Int, var kor:Int, var eng:Int, var math:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(grade)
        parcel.writeInt(kor)
        parcel.writeInt(eng)
        parcel.writeInt(math)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentData> {
        override fun createFromParcel(parcel: Parcel): StudentData {
            return StudentData(parcel)
        }

        override fun newArray(size: Int): Array<StudentData?> {
            return arrayOfNulls(size)
        }
    }

}