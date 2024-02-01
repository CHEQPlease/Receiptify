package com.cheq.receiptify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.cheq.receiptify.sampleapp.R
import com.cheq.receiptify.sampleapp.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = """
{
  "brandName": "CHEQ DINER",
  "customerName": null,
  "orderType": "",
  "orderSubtitle": null,
  "totalItems": "Total Items : 1",
  "orderNo": "Order #: H5",

  "deviceType": "handheld",
  "receiptType": "KITCHEN",
  "timeOfOrder": "Placed at : 10/16/2023 04:14 AM EDT",
  "footerText": null,
  "paymentQRLink": null,
  "isRefunded": null,
  "isReprinted": true,
  "printStatusText": "**UPDATED**",
  "supportInfo": "",
  "offlineHeaderMsg": "",
  "items": [
    {
      "itemName": "Toast and Poached Eggs",
      "description": "",
      "quantity": "x1",
      "quantityExtra": null,
      "price": "${'$'}4.00",
      "strikethrough": false
    }
  ],
  "breakdown": [
    {
      "key": "PAYMENT TYPE",
      "value": "Card",
      "important": null
    },
    {
      "key": "SUB TOTAL",
      "value": "${'$'}4.00",
      "important": null
    },
    {
      "key": "SERVICE CHARGES",
      "value": "${'$'}0.88",
      "important": null
    },
    {
      "key": "TAX",
      "value": "${'$'}0.39",
      "important": null
    },
    {
      "key": "CONVENIENCE FEE",
      "value": "${'$'}0.23",
      "important": null
    },
    {
      "key": "GRAND TOTAL",
      "value": "${'$'}5.50",
      "important": true
    }
  ]
}
        """.trimIndent()
        Receiptify.init(this)
        val receiptBitmap = Receiptify.buildReceipt(jsonString)
        binding.ivReceipt.setImageBitmap(receiptBitmap)

    }
}