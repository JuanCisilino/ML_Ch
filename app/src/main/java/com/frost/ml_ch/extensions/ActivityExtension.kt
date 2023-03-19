package com.frost.ml_ch.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.frost.ml_ch.R

fun showToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showAlert(){
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.error))
    builder.setMessage(getString(R.string.error_message))
    builder.setPositiveButton(getString(R.string.ok), null)
    val dialog = builder.create()
    dialog.show()
}