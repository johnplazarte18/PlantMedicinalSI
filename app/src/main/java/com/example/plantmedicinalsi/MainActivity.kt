package com.example.plantmedicinalsi

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var btnAnalizar: Button
    lateinit var img_view : ImageView
    lateinit var bitmap: Bitmap
    lateinit var txtNota: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_PlantMedicinalSI)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSelImagen=findViewById<Button>(R.id.btnSelImagen)
        val btnVerCatalogo=findViewById<Button>(R.id.btnVerCatalogo)
        btnAnalizar=findViewById<Button>(R.id.btnAnalizar)
        val btnCamara=findViewById<Button>(R.id.btnCapImagen)
        img_view=findViewById<ImageView>(R.id.imvResult)
        txtNota=findViewById<TextView>(R.id.txtNota)

        btnAnalizar.visibility=View.GONE
        btnCamara.setOnClickListener(View.OnClickListener {
            var camera : Intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, 200)
        })
        btnSelImagen.setOnClickListener(View.OnClickListener {
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        })
        btnAnalizar.setOnClickListener{
            val intent1 = Intent(this, Resultado::class.java)
            startActivity(intent1)
        }
        btnVerCatalogo.setOnClickListener{
            val intent1 = Intent(this, catologo::class.java)
            startActivity(intent1)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250){
            img_view.setImageURI(data?.data)

            var uri : Uri?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            btnAnalizar.visibility=View.VISIBLE
            txtNota.visibility=View.GONE
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            img_view.setImageBitmap(bitmap)
            btnAnalizar.visibility=View.VISIBLE
            txtNota.visibility=View.GONE
        }

    }
}