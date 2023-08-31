package com.elmohandes.storeegypt.utils

import android.app.Activity
import android.app.AlertDialog
import com.elmohandes.storeegypt.R

class CustomProgressDialog(private val activity: Activity) {

    private lateinit var dialog: AlertDialog

    fun startLoading(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.custum_progress_bar,null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

    }

    fun dismissDialog(){
        dialog.dismiss()
    }

}