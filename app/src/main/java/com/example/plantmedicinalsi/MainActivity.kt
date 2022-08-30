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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.plantmedicinalsi.ml.ModelUnquant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {
    private lateinit var btnAnalizar: Button
    lateinit var img_view : ImageView
    lateinit var bitmap: Bitmap
    lateinit var txtNota: TextView

    var imageSize = 224
    override fun onCreate(savedInstanceState: Bundle?) {
        //Thread.sleep(2000)
        //setTheme(R.style.Theme_PlantMedicinalSI)

        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val btnSelImagen=findViewById<ImageButton>(R.id.btnSelImagen)
        val btnVerCatalogo=findViewById<ImageButton>(R.id.btnVerCatalogo)
        btnAnalizar=findViewById<Button>(R.id.btnAnalizar)
        val btnCamara=findViewById<ImageButton>(R.id.btnCapImagen)
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
            bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
            outputGenerator(bitmap)
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

    private  fun outputGenerator(bitmap: Bitmap){
        val model = ModelUnquant.newInstance(applicationContext)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val valor = intValues[pixel++] // RGB
                byteBuffer.putFloat((valor shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((valor shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((valor and 0xFF) * (1f / 255f))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)
        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        // Releases model resources if no longer used.
        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for (i in 0 until confidences.size) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf("plt1", "plt2", "plt3")
        var s: String? = ""
        for (i in 0 until classes.size) {
            s += java.lang.String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
        }
        model.close()

    }

}