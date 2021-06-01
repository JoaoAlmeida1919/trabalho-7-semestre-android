package br.com.joao.almeida.authenticator.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import br.com.joao.almeida.authenticator.R
import br.com.joao.almeida.authenticator.model.Usuario
import android.util.Base64;
import android.widget.BaseAdapter
import android.widget.TextView

class UsuarioAdapter(context: Context,   usuarios: ArrayList<Usuario>) :
        BaseAdapter() {

    private val myContex : Context
    private  val myUsers : ArrayList<Usuario>

    init {
        myContex = context
        myUsers = usuarios
    }


    private fun setImage(contextView: View, usuario: Usuario) {
        if(!usuario.urlImage.isNullOrEmpty()) {
            var decodedString : ByteArray? = Base64.decode(usuario.urlImage, Base64.DEFAULT)
            var decodedByte: Bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString!!.size);
            contextView.findViewById<ImageView>(R.id.userPhoto).setImageBitmap(decodedByte)
        }

    }

    override fun getCount(): Int {
        return myUsers.size
    }

    override fun getItem(position: Int): Usuario {
        return myUsers.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, p1: View?, viewGroup: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(myContex)
        val rowItem = layoutInflater.inflate(R.layout.activity_item_list, viewGroup, false)
        val usuario = myUsers.get(position)
        setImage(rowItem, usuario)
        val textName = rowItem.findViewById<TextView>(R.id.labelNome)
        val textEmail = rowItem.findViewById<TextView>(R.id.labelEmail)
        val textDataNascimento = rowItem.findViewById<TextView>(R.id.labelDataNascimento)
        textEmail.text = usuario.email
        textName.text = usuario.nome
        textDataNascimento.text = usuario.dataNacimento
        return rowItem
    }

    fun getPosition(usuario: Usuario?) : Int {
        return myUsers.indexOf(usuario)
    }

    fun add(usuario: Usuario?) {
        myUsers.add(usuario!!)
    }

    fun remove(usuario: Usuario?) {
        myUsers.remove(usuario)
    }

    fun getList() : ArrayList<Usuario> {
        return myUsers
    }
}