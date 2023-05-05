package com.cheq.receiptify.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.View
import com.cheq.receiptify.data.ReceiptDTO
import com.google.gson.Gson


object Utils {

    fun convertMmToPixel(mm: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, context.resources.displayMetrics).toInt()
    }

    fun generateBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun getReceiptDTO(receiptDTOJSON: String): ReceiptDTO? {
        return try {
            Gson().fromJson(receiptDTOJSON, ReceiptDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}