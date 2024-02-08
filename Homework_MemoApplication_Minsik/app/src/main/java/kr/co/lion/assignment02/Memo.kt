package kr.co.lion.assignment02

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class Memo(var title:String?, var content:String?) : Parcelable {
    val date = Date(System.currentTimeMillis())

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Memo> {
        override fun createFromParcel(parcel: Parcel): Memo {
            return Memo(parcel)
        }

        override fun newArray(size: Int): Array<Memo?> {
            return arrayOfNulls(size)
        }
    }
}