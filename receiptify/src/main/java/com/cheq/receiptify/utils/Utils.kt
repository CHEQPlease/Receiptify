package com.cheq.receiptify.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.TypedValue
import android.view.View
import com.cheq.receiptify.data.ReceiptDTO
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


object Utils {

    fun convertMmToPixel(mm: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, context.resources.displayMetrics).toInt()
    }

    fun generateBitmap(view: View,highQuality: Boolean = false): Bitmap {

        val bitmapConfig : Bitmap.Config = if (highQuality) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(view.width, view.height, bitmapConfig)
        val canvas = Canvas(bitmap)

        view.draw(canvas)
        return bitmap
    }

    @Throws(WriterException::class)
    fun generateQRBitmap(text: String?, width: Int, height: Int): Bitmap? {
        val writer = QRCodeWriter()
        val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height)
        val w: Int = bitMatrix.width
        val h: Int = bitMatrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                pixels[y * w + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
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