package br.com.joao.almeida.authenticator

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import br.com.joao.almeida.authenticator.model.Usuario
import java.io.ByteArrayOutputStream

class UsuarioDetalheActivity : AppCompatActivity() {


    val EXTRA_USUARIO = "br.com.joao.almeida.authenticator.USUARIO"
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalhe)
        usuario = intent.getParcelableExtra<Usuario>(EXTRA_USUARIO)!!
        Log.d("Usuario_DETAIL", usuario.toString() + "é vazia")
        var textViewNome = findViewById<TextView>(R.id.textViewNome)
        var textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        var textViewData = findViewById<TextView>(R.id.textViewData)
        var imageView = findViewById<ImageView>(R.id.imagemUserDatails)

        if(usuario != null) {
            textViewNome.text = usuario.nome
            textViewEmail.text = usuario.email
            textViewData.text = usuario.dataNacimento
            if(usuario.urlImage != null) {

            }
        }

    }

    fun tirarFoto(){
        var takePictureIntent:Intent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                var imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap
                findViewById<ImageView>(R.id.imagemUserDatails).setImageBitmap(imageBitmap);
                var byteArrayOutputStream : ByteArrayOutputStream = ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                var byteArray = byteArrayOutputStream.toByteArray();
                var encoded : String = Base64.encodeToString(byteArray, Base64.DEFAULT);
                usuario.urlImage = encoded;
            }
        } catch (e: Exception) {
        }
    }

    fun saveUser() {

    }
}