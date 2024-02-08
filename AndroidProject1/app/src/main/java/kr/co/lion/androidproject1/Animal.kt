package kr.co.lion.androidproject1

import android.os.Parcel
import android.os.Parcelable

abstract class Animal(open val type:String?, open val name:String?, open val age:Int):Parcelable{

    abstract fun getInfo():String
}