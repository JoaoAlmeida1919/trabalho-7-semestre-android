package br.com.joao.almeida.authenticator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import br.com.joao.almeida.authenticator.model.Usuario
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ListaActivity : AppCompatActivity() {


    val EXTRA_USUARIO = "br.com.joao.almeida.authenticator.USUARIO"
    private lateinit var databaseReference : DatabaseReference
    private lateinit var listContact: ListView
    private lateinit var adapter: ArrayAdapter<Usuario>
    private  lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        adapter = ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, ArrayList<Usuario>())
        listContact = findViewById(R.id.listViewUsuario)
        listContact.adapter = adapter
        spinner = findViewById(R.id.progressBarList)

    }

    override fun onStart() {
        super.onStart()
        databaseReference = Firebase.database("https://authenticator-b2752-default-rtdb.firebaseio.com/").getReference("usuarios")
        adicionarObaservadores()
        listContact.setOnItemClickListener { parent, view, position, id ->
            abrirUsuarioDetalhe(adapter.getItem(id.toInt()))
        }
    }

    private fun adicionarObaservadores() {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
                val usuario = dataSnapshot.getValue<Usuario>()
                if(adapter.getPosition(usuario) == -1) {
                    adapter.add(usuario)
                }

            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
                val newComment = dataSnapshot.getValue<Usuario>()
                val commentKey = dataSnapshot.key

            }
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                adapter.remove(usuario)
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseReference.addChildEventListener(childEventListener)
        spinner.visibility = View.GONE
    }

    companion object {
        private var TAG = "PAGELIST"
    }

    private fun basicQueryValueListener() {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (usuarioSnapshot in dataSnapshot.children) {
                   var usuario = usuarioSnapshot.getValue(Usuario::class.java)
                    if(adapter.getPosition(usuario) > -1) {
                        adapter.add(usuario)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun abrirUsuarioDetalhe(usuario: Usuario?) {
        val intent = Intent(this, UsuarioDetalheActivity::class.java).apply {
            putExtra(EXTRA_USUARIO, usuario)
        }
        startActivity(intent)
    }
}