package br.com.joao.almeida.authenticator

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.NavUtils
import br.com.joao.almeida.authenticator.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CadastroActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CadastroActivity"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordConfirmation: EditText
    private  lateinit var editTextNome: EditText
    private lateinit var editTextDataNascimento: EditText
    private lateinit var usuario: Usuario

    private  lateinit var spinner : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        auth = Firebase.auth
        spinner = findViewById(R.id.progressBarCadastro)
        spinner.visibility = View.GONE

    }

    override fun onStart() {
        super.onStart()
        editTextEmail = findViewById(R.id.textEmail)
        editTextDataNascimento = findViewById(R.id.editTextDate)
        editTextNome = findViewById(R.id.editTextName)
        editTextPassword = findViewById(R.id.textPassword)
        editTextPasswordConfirmation = findViewById(R.id.textPasswordConfirm)
    }

    private fun createAccount(email: String, password: String) {
        spinner.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    spinner.visibility = View.GONE
                    Log.w("telaCadastro", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(applicationContext, "Falha na autenticação",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    fun createAcount(v: View) {
        hideKeyBoard(this, v)

        if(!verificarInputs()) {
            return
        }
        if(editTextPassword.text.toString().equals(editTextPassword.text.toString())) {
            createAccount(editTextEmail.text.toString(), editTextPassword.text.toString())
        } else {
            spinner.visibility = View.GONE
            Log.d("Tela cadastro", "Senha não conferem")
            val toast = Toast.makeText(applicationContext, "Senhas não conferem", Toast.LENGTH_LONG)
            toast.show()

        }
    }

    private fun verificarInputs(): Boolean {
        if(verificaVazio(editTextNome)
            || verificaVazio(editTextEmail)
            || verificaVazio(editTextPassword)
            || verificaVazio(editTextPasswordConfirmation)
            || verificaVazio(editTextDataNascimento)
        ) {
            spinner.visibility = View.GONE
            val toast = Toast.makeText(applicationContext, "Favor inserir dados cadastrais", Toast.LENGTH_LONG)
            toast.show()
            return false
        }

        usuario = Usuario(
            editTextNome.text.toString(),
            editTextEmail.text.toString(),
            editTextDataNascimento.text.toString(),
        ""
        )
        return true;
    }

    private fun verificaVazio(editText: EditText): Boolean {
        return editText.text.isNullOrBlank()
    }

    private fun updateUI(user: FirebaseUser?) {
        if(usuario != null && user != null) {
            var database = Firebase.database("https://authenticator-b2752-default-rtdb.firebaseio.com/")
            var ref = database.getReference("usuarios").child(user.uid)
            ref.setValue(usuario)
            Log.d("Usuário criado", user.email.toString())
            spinner.visibility = View.GONE
            val toast = Toast.makeText(applicationContext, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG)
            toast.show()
        }

    }

    fun hideKeyBoard(context: Context, view: View) {
        val imm : InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    fun returnToParent(v: View) {
        var upIntent: Intent? = NavUtils.getParentActivityIntent(this)
        NavUtils.navigateUpTo(this, upIntent!!)
    }

}