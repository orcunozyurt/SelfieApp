package com.nerdz.selfieapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream

fun createBitmap(context: Context, imagePath: String): Bitmap {
    val assetManager = context.assets
    var inputStream: InputStream? = null
    try {
        inputStream = assetManager.open(imagePath)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return BitmapFactory.decodeStream(inputStream)!!
}