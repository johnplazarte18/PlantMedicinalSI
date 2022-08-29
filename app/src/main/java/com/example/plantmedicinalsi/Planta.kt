package com.example.plantmedicinalsi

import android.os.Parcel
import android.os.Parcelable

data class Planta(val id:String,
                  val nombrePlanta:String,
                  val nombreCientifico:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) { }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombrePlanta)
        parcel.writeString(nombreCientifico)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Planta> {
        override fun createFromParcel(parcel: Parcel): Planta {
            return Planta(parcel)
        }

        override fun newArray(size: Int): Array<Planta?> {
            return arrayOfNulls(size)
        }
    }
}

