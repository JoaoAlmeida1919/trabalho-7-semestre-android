package br.com.joao.almeida.authenticator.utils

import android.app.ProgressDialog
import android.content.Context

private lateinit var dialog: ProgressDialog

abstract class Loader {
    fun showLoader(context: Context) {
        dialog
    }
}