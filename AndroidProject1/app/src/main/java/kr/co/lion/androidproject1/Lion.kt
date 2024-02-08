package kr.co.lion.androidproject1

import android.os.Parcel
import android.os.Parcelable

class Lion(override val type:String?, override val name:String?, override val age:Int, val hair:Int, val gender:String?):Animal(type,name,age),Parcelable{

    override fun getInfo():String {
        return "동물 종류 : 사자\n" +
                "이름 : ${name}\n" +
                "나이 : ${age}\n" +
                "털의 개수 : ${hair}\n" +
                "성별 : ${gender}"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeInt(hair)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lion> {
        override fun createFromParcel(parcel: Parcel): Lion {
            return Lion(parcel)
        }

        override fun newArray(size: Int): Array<Lion?> {
            return arrayOfNulls(size)
        }
    }

}