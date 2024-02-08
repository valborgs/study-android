package kr.co.lion.androidproject1

import android.os.Parcel
import android.os.Parcelable

class Tiger(override val type:String?, override val name:String?, override val age:Int, val stripe:Int, val weight:Double):Animal(type,name,age),Parcelable {

    override fun getInfo():String {
        return "동물 종류 : 호랑이\n" +
                "이름 : ${name}\n" +
                "나이 : ${age}\n" +
                "줄무늬 개수 : ${stripe}개\n" +
                "몸무게 : ${weight}kg\n"
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeInt(stripe)
        parcel.writeDouble(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tiger> {
        override fun createFromParcel(parcel: Parcel): Tiger {
            return Tiger(parcel)
        }

        override fun newArray(size: Int): Array<Tiger?> {
            return arrayOfNulls(size)
        }
    }
}