package kr.co.lion.androidproject1test

import android.os.Parcel
import android.os.Parcelable

open class Animal() : Parcelable{
    // 동물 타입
    var type = AnimalType.ANIMAL_TYPE_LION
    // 이름
    var name = ""
    // 나이
    var age = 0

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()!!
        age = parcel.readInt()
    }

    // Parcel에 프로퍼티의 값을 담아준다.
    fun addToParcel(parcel:Parcel){
        parcel.writeValue(type)
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    // Parcel로부터 데이터를 추출하여 프로퍼티에 담아준다.
    fun getFromParcel(parcel: Parcel){
        type = parcel.readValue(AnimalType::class.java.classLoader) as AnimalType
        name = parcel.readString()!!
        age = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Animal> {
        override fun createFromParcel(parcel: Parcel): Animal {
            return Animal(parcel)
        }

        override fun newArray(size: Int): Array<Animal?> {
            return arrayOfNulls(size)
        }
    }
}