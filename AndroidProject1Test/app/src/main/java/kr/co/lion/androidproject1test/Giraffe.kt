package kr.co.lion.androidproject1test

import android.os.Parcel
import android.os.Parcelable

class Giraffe() : Animal(), Parcelable {

    // 목의 길이
    var neckLength = 0
    // 달리는 속도
    var runSpeed = 0

    constructor(parcel: Parcel) : this() {
        getFromParcel(parcel)
        neckLength = parcel.readInt()
        runSpeed = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        addToParcel(parcel)
        parcel.writeInt(neckLength)
        parcel.writeInt(runSpeed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Giraffe> {
        override fun createFromParcel(parcel: Parcel): Giraffe {
            return Giraffe(parcel)
        }

        override fun newArray(size: Int): Array<Giraffe?> {
            return arrayOfNulls(size)
        }
    }

}