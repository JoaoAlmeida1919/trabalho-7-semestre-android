package br.com.joao.almeida.authenticator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import br.com.joao.almeida.authenticator.model.Usuario

class UsuarioDetalheActivity : AppCompatActivity() {


    val EXTRA_USUARIO = "br.com.joao.almeida.authenticator.USUARIO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalhe)
        val usuario: Usuario? = intent.getParcelableExtra<Usuario>(EXTRA_USUARIO)
        Log.d("Usuario_DETAIL", usuario.toString() + "é vazia")
        var textViewNome = findViewById<TextView>(R.id.textViewNome)
        var textViewEmail = findViewById<TextView>(R.id.textViewEmail)
        var textViewData = findViewById<TextView>(R.id.textViewData)

        if(usuario != null) {
            textViewNome.text = usuario.nome
            textViewEmail.text = usuario.email
            textViewData.text = usuario.dataNacimento
        }

    }
}