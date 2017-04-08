package com.nulp.zamahaka.kursova.controller

import android.content.Context
import android.support.v7.app.AlertDialog
import com.nulp.zamahaka.kursova.R

/**
 * Created by Ura on 08.04.2017.
 */
class NoInternetController(context: Context) {

    private val mDialog = AlertDialog.Builder(context)
            .setMessage(R.string.message_no_internet)
            .setPositiveButton(R.string.ok, null)
            .create()

    fun show() {
        if (!mDialog.isShowing) mDialog.show()
    }

    fun dissmis() = mDialog.dismiss()

}