package com.example.plantmedicinalsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InfoPlant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_plant)

        val planta=intent.getParcelableExtra<Planta>("planta")
        if(planta != null){
            val txtNombrePlanta:TextView=findViewById(R.id.txtNombrePlantaSel)
            txtNombrePlanta.text=planta.id
        }
    }
}