package com.example.plantmedicinalsi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern

class CustomAdapter(var context: Context):RecyclerView.Adapter<CustomAdapter.ViewHolder>(){


    val ltPlantas = mutableListOf<Planta>()
    var listaLlena=false

    var onItemClick:((Planta) ->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.card_plants_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planta=ltPlantas[position]
        holder.itemTitle.text=planta.nombrePlanta
        holder.itemSubTitle.text=planta.nombreCientifico
        holder.itemImage.setImageResource(R.drawable.ic_launcher_foreground)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(planta)
        }
    }

    override fun getItemCount(): Int {
        if(!listaLlena){
            llenarLista()
        }
        return ltPlantas.size
    }

    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemSubTitle:TextView

        init {
            itemImage=itemView.findViewById(R.id.item_image)
            itemTitle=itemView.findViewById(R.id.item_title)
            itemSubTitle=itemView.findViewById(R.id.item_subtitle)
        }
    }
    private fun llenarLista() {
        val minput = InputStreamReader(context.assets.open("plantas.csv"))
        val reader = BufferedReader(minput)

        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val row = Pattern.compile(";").split(line)
            var planta = Planta(row[0],row[1],row[2])
            ltPlantas.add(planta)
        }
        listaLlena=true
    }
}

