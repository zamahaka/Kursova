package com.nulp.zamahaka.kursova.controller

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.nulp.zamahaka.kursova.R

/**
 * Created by Ura on 08.04.2017.
 */
class ErrorController(context: Context, @StringRes messageRes: Int) {

    private val mDialog = AlertDialog.Builder(context)
            .setMessage(messageRes)
            .setPositiveButton(R.string.ok, null)
            .show()

    fun show() {
        if (!mDialog.isShowing) mDialog.show()
    }

    fun dissmis() = mDialog.dismiss()

}