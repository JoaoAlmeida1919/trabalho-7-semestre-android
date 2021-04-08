package br.com.joao.almeida.authenticator.model

import android.os.Parcel
import android.os.Parcelable

class Usuario(val nome: String?, val email: String?, val dataNacimento: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    public constructor() : this(
        "",
    "",
    ""
    )

    override fun toString(): String {
        if(this.nome != null)
            return this.nome
        return ""
    }

    override fun equals(other: Any?): Boolean {
        return this.email.equals((other as Usuario).email)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(email)
        parcel.writeString(dataNacimento)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

}