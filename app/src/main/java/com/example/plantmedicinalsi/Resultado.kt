package com.example.plantmedicinalsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.Bitmap

import android.content.Intent
import android.os.Parcelable
import android.graphics.BitmapFactory





class Resultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)
        val bmp: Bitmap

        val byteArray = intent.getByteArrayExtra("image")
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
    }
}