package kr.co.lion.androidproject1

import android.os.Parcel
import android.os.Parcelable

class Giraff(override val type:String?, override val name:String?, override val age:Int, val neck:Int, val speed:Int):Animal(type,name,age),Parcelable {

    override fun getInfo():String {
        return "동물 종류 : 기린\n" +
                "이름 : ${name}\n" +
                "나이 : ${age}\n" +
                "목의 길이 : ${neck}\n" +
                "달리는 속도 : 시속 ${speed}km"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeInt(neck)
        parcel.writeInt(speed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Giraff> {
        override fun createFromParcel(parcel: Parcel): Giraff {
            return Giraff(parcel)
        }

        override fun newArray(size: Int): Array<Giraff?> {
            return arrayOfNulls(size)
        }
    }
}