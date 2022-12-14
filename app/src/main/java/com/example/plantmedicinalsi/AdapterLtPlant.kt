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
import android.graphics.drawable.Drawable
import java.io.InputStream


class AdapterLtPlant(var context: Context):RecyclerView.Adapter<AdapterLtPlant.ViewHolder>(){


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

        val ims: InputStream = context.getAssets().open("imgPlantas/"+planta.imagen)
        val d = Drawable.createFromStream(ims, null)
        holder.itemImage.setImageDrawable(d)

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
            itemImage=itemView.findViewById(R.id.item_image_c)
            itemTitle=itemView.findViewById(R.id.item_title_c)
            itemSubTitle=itemView.findViewById(R.id.item_subtitle_c)
        }
    }
    private fun llenarLista() {
        val minput = InputStreamReader(context.assets.open("plantas.csv"))
        val reader = BufferedReader(minput)

        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val row = Pattern.compile(";").split(line)
            var planta = Planta(row[0],row[1],row[2],row[3],0f)
            ltPlantas.add(planta)
        }
        listaLlena=true
    }
}

