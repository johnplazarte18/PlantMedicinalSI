package com.example.plantmedicinalsi

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern
import android.text.method.ScrollingMovementMethod




class InfoPlant : AppCompatActivity() {
    var texto:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_info_plant)

        val planta=intent.getParcelableExtra<Planta>("planta")
        if(planta != null){
            llenarBeneficios(planta.id)
            val txtPlantaSel:TextView=findViewById(R.id.txtPlantaSel)
            val txtNombreCientifico:TextView=findViewById(R.id.txtNombreCientifico)
            val txtBeneficios:TextView=findViewById(R.id.txtBeneficios)
            val img_sel:ImageView=findViewById(R.id.img_sel)
            txtPlantaSel.text=planta.nombrePlanta
            txtNombreCientifico.text=planta.nombreCientifico

            var ims: InputStream = getAssets().open("imgPlantas/"+planta.imagen)
            val d = Drawable.createFromStream(ims, null)
            img_sel.setImageDrawable(d)

            txtBeneficios.text=Html.fromHtml(texto)
            txtBeneficios.movementMethod = ScrollingMovementMethod()

        }
    }
    private fun llenarBeneficios(id: String) {
        val minput = InputStreamReader(assets.open("beneficios.csv"))
        val reader = BufferedReader(minput)

        var line: String?
        var i=0
        while (reader.readLine().also { line = it } != null) {
            val row = Pattern.compile(";").split(line)
            if(id==row[0]){
                i++
                texto+="<hr><b>INFORMACIÓN "+i+"</b><br>● Preparación <br>"+row[1]+"<br>● Dosis <br>"+row[2]+"<br>● Beneficios<br>"+row[3]+"<br><br>"
            }
        }
    }
}