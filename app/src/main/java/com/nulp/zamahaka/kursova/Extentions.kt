package com.nulp.zamahaka.kursova

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by Ura on 26.03.2017.
 */

fun Picasso.loadAsset(path: String): RequestCreator {
    return this.load("file:///android_asset/$path")
}

fun ImageView.loadFromAsset(path: String) {
    Picasso.with(context).loadAsset(path).fit().centerCrop().into(this)
}

fun ImageView.load(url: String) {
    Picasso.with(context).load(url).fit().centerCrop().into(this)
}

fun ImageView.load(url: String, @DrawableRes id: Int) {
    Picasso.with(context).load(url).fit().centerCrop().placeholder(id).into(this)
}

fun ImageView.loadFromAssetNoFit(path: String) {
    Picasso.with(context).loadAsset(path).into(this)
}