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
  "brandName": null,
  "customerName": null,
  "orderType": null,
  "orderSubtitle": null,
  "totalItems": null,
  "orderNo": null,
  "tableNo": null,
  "deviceType": "pos",
  "receiptType": "SERVER_TIPS",
  "timeOfOrder": "Placed at : 07/29/2024 11:23 AM AKDT",
  "footerText": null,
  "paymentQRLink": null,
  "isRefunded": null,
  "isReprinted": null,
  "printStatusText": null,
  "supportInfo": "",
  "offlineHeaderMsg": "",
  "serverTipInfo": {
    "serverName": "CHEQ Diner",
    "serverId": "",
    "tipInfoBreakdown": [
      {
        "key": "Sign in time",
        "value": "July 30, 2024 1:08 AM"
      },
      {
        "key": "Sign out time",
        "value": ""
      },
      {
        "key": "Print time",
        "value": "July 30, 2024 1:08 AM"
      },
      {
        "key": "Device Name",
        "value": "H_027_The_CHEQ_Diner"
      }
    ]
  }
}
        """.trimIndent()
        Receiptify.init(this)
        val receiptBitmap = Receiptify.buildReceipt(jsonString)
        binding.ivReceipt.setImageBitmap(receiptBitmap)

    }
}