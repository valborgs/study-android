package kr.co.lion.androidproject1test

import android.os.Parcel
import android.os.Parcelable

class Tiger() : Animal(), Parcelable {

    // 줄무늬 개수
    var lineCount = 0
    // 몸무게
    var weight = 0

    constructor(parcel: Parcel) : this() {
        getFromParcel(parcel)
        lineCount = parcel.readInt()
        weight = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        addToParcel(parcel)
        parcel.writeInt(lineCount)
        parcel.writeInt(weight)
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