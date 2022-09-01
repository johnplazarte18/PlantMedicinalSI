package com.example.plantmedicinalsi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class catologo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_catologo)


        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        val adapter=AdapterLtPlant(this)

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter

        adapter.onItemClick={
            val intent = Intent(this, InfoPlant::class.java)
            intent.putExtra("planta",it)
            startActivity(intent)
        }
    }




}