package com.example.plantmedicinalsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.graphics.Bitmap

import android.content.Intent
import android.os.Parcelable
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern


class Resultado : AppCompatActivity() {

    val ltPlantas = mutableListOf<Planta>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val floatArray = intent.getFloatArrayExtra("probabilidad")
        if (floatArray != null) {
            asignarCoincidencia(floatArray)
        }
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerViewC)
        val adapter=AdapterLtPlantCoincidencia(this,ltPlantas)

        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }

    private fun asignarCoincidencia(floatArray: FloatArray) {
        val minput = InputStreamReader(assets.open("plantas.csv"))
        val reader = BufferedReader(minput)

        var line: String?
        var i=0
        while (reader.readLine().also { line = it } != null) {
            val row = Pattern.compile(";").split(line)
            var planta = Planta(row[0],row[1],row[2],row[3],floatArray[i])
            ltPlantas.add(planta)
            i++
        }
        ltPlantas.sortByDescending { it.coincidencia }
    }

}