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
import android.widget.ProgressBar
import java.io.InputStream
import java.math.RoundingMode
import java.text.DecimalFormat


class AdapterLtPlantCoincidencia(var context: Context,var ltPlantas: MutableList<Planta>):RecyclerView.Adapter<AdapterLtPlantCoincidencia.ViewHolder>(){
    var onItemClick:((Planta) ->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.card_plants_coincidence_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val planta=ltPlantas[position]
        holder.itemTitle.text=planta.nombrePlanta
        holder.itemSubTitle.text=planta.nombreCientifico

        val number2digits:Double = Math.round(planta.coincidencia * 100.0) / 100.0

        holder.itemCoincidencia.text="Coincidencia: "+(number2digits*100)+"%"
        holder.itemNivelCoincidencia.setProgress((planta.coincidencia * 100.0).toInt())

        val ims: InputStream = context.getAssets().open("imgPlantas/"+planta.imagen)
        val d = Drawable.createFromStream(ims, null)
        holder.itemImage.setImageDrawable(d)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(planta)
        }
    }

    override fun getItemCount(): Int {
        return ltPlantas.size
    }

    inner class  ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemSubTitle:TextView
        var itemNivelCoincidencia:ProgressBar
        var itemCoincidencia:TextView

        init {
            itemImage=itemView.findViewById(R.id.item_image_c)
            itemTitle=itemView.findViewById(R.id.item_title_c)
            itemSubTitle=itemView.findViewById(R.id.item_subtitle_c)
            itemCoincidencia=itemView.findViewById(R.id.txtCoincidencia)
            itemNivelCoincidencia=itemView.findViewById(R.id.pgbNivelCoincidencia)
        }
    }
}

