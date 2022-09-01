package com.example.plantmedicinalsi

import android.os.Parcel
import android.os.Parcelable

data class Planta(val id:String,
                  val nombrePlanta:String,
                  val nombreCientifico:String,
                  val imagen:String,
                  val coincidencia:Float): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()!!
    ) { }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombrePlanta)
        parcel.writeString(nombreCientifico)
        parcel.writeString(imagen)
        parcel.writeString(coincidencia.toString())
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

