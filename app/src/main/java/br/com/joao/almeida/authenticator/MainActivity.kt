package br.com.joao.almeida.authenticator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.Nullable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val EXTRA_PESSOA = "br.com.joao.almeida.authenticator.PESSOA"
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextTextPassword:EditText
    private lateinit var editTextTextEmail:EditText
    private  lateinit var spinner: ProgressBar

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        spinner = findViewById(R.id.progressBar1)
        spinner.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth

        if(auth.currentUser != null) {
        //    abrirTelaLista()
        }
    }

    private fun reload() {
        TODO("Not yet implemented")
    }


    private fun abrirTelaCadastro() {
        val intent = Intent(this, CadastroActivity::class.java).apply{}
        startActivity(intent)
    }

    fun abrirTelaCadastro(v: View) {
        abrirTelaCadastro()
    }

    fun abrirTelaLista() {
        val intent = Intent(this, ListaActivity::class.java).apply{}
        startActivity(intent)
    }

    fun fazerLogin(v: View) {
        spinner.setVisibility(View.VISIBLE)
        editTextTextEmail = findViewById(R.id.editTextTextEmailAddress)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)

        if(editTextTextEmail.text.toString().isNullOrBlank() || editTextTextPassword.text.isNullOrBlank()) {
            Toast.makeText(applicationContext, "Insira email e senha",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(editTextTextEmail.text.toString(), editTextTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    spinner.setVisibility(View.GONE)
                    abrirTelaLista()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    spinner.setVisibility(View.GONE)
                }
            }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}