package com.cheq.receiptify.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import com.cheq.receiptify.data.ReceiptDTO
import com.cheq.receiptify.enums.TargetPlatform
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel


object Utils {
    private const val POS_MIN_REQUIRED_WIDTH = 574  // Minimum width for POS receipts

    fun convertMmToPixel(mm: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, context.resources.displayMetrics).toInt()
    }

    fun generateBitmap(view: View, targetPlatform : TargetPlatform, highQuality: Boolean = false): Bitmap {

        val scaleFactor = if (targetPlatform == TargetPlatform.POS) {
            maxOf(2f, POS_MIN_REQUIRED_WIDTH.toFloat() / view.width)
        } else {
            1f
        }
        
        // Calculate dimensions
        val width = (view.width * scaleFactor).toInt()
        val height = (view.height * scaleFactor).toInt()
        
        // Create bitmap with high quality config
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Scale everything up
        canvas.scale(scaleFactor, scaleFactor)
        
        // Draw the view
        view.draw(canvas)
        
        return bitmap
    }

    @Throws(WriterException::class)
    fun generateQRBitmap(context: Context, text: String?, widthMm: Float, heightMm: Float): Bitmap? {
        // Convert mm to pixels
        val widthPx = convertMmToPixel(widthMm, context)
        val heightPx = convertMmToPixel(widthMm, context)

        val writer = QRCodeWriter()

        val hints = hashMapOf<EncodeHintType, Any>().apply {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
            put(EncodeHintType.MARGIN, 0) // Set quiet zone to minimum
        }

        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, widthPx, heightPx, hints)
        val w = bitMatrix.width
        val h = bitMatrix.height
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

    fun getHTMLFormattedString(data: String?): Spanned? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(data)
        }
    }
}