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
          "brandName": "CHEQ Diner",
          "orderType": "Self-Order",
          "orderSubtitle": "Kiosk-Order",
          "totalItems": "2",
          "orderNo": "K10",
          "tableNo": "234",
          "receiptType": "server_tips",
          "deviceType": "pos",
          "timeOfOrder": "Placed at : 01/12/2023 03:57 AM AKST",
          "items": [
            {
              "itemName": "Salmon Fry",
              "description": "  -- Olive\n  -- Deep Fried Salmon\n  -- ADD Addition 1\n  -- no Nuts\n  -- no Olive Oil\n  -- Substitution 1 SUB\n  -- allergy 1 ALLERGY\n",
              "quantity": "231",
              "price": "${'$'}10000.0",
              "strikethrough": false
            },
            {
              "itemName": "Water + Apple Pay",
              "description": "  -- Onions\n",
              "quantity": "1",
              "price": "${'$'}1.0",
              "strikethrough": true
            }
          ],
          "breakdown": [
            {
              "key": "Payment Type",
              "value": "Card",
              "important": null
            },
            {
              "key": "Card Type",
              "value": "mc",
              "important": null
            },
            {
              "key": "Card #:",
              "value": "541333 **** 9999",
              "important": null
            },
            {
              "key": "Card Entry",
              "value": "CONTACTLESS",
              "important": null
            },
            {
              "key": "",
              "value": "",
              "important": null
            },
            {
              "key": "Sub Total",
              "value": "${'$'}21.01",
              "important": null
            },
            {
              "key": "Area Tax",
              "value": "${'$'}1.00",
              "important": null
            },
            {
              "key": "VAT",
              "value": "${'$'}2.10",
              "important": null
            },
            {
              "key": "Customer Fee",
              "value": "${'$'}0.63",
              "important": null
            },
            {
              "key": "Service Fee",
              "value": "${'$'}0.91",
              "important": null
            },
            {
              "key": "Tax",
              "value": "${'$'}0.01",
              "important": null
            },
            {
              "key": "GRAND TOTAL",
              "value": "${'$'}25.66",
              "important": true
            }
          ],
          "serverTipInfo": {
            "serverName": "Shorol",
            "serverId": "123333330022",
            "totalTip": " $200.19",
            "tipInfoBreakdown": [
              {
                "key": "Placed at",
                "value": "24,april, 2023"
              },
              {
                "key": "Device Name",
                "value": "Kiosk_1"
              },
              {
                "key": "Device Name",
                "value": "KIOSK_14"
              },
              {
                "key": "Device Name",
                "value": "KIOSK_14"
              }
            ],
            "tipPerRevenueCenter": [
              {
                "name": "Obie's",
                "tip": "${'$'}120",
                "deviceList": [
                  "KIOSK_1",
                  "KIOSK_2",
                  "KIOSK_3"
                ]
              },
              {
                "name": "Python Cook House",
                "tip": "${'$'}210.22",
                "deviceList": [
                  "KIOSK_4",
                  "KIOSK_2",
                  "KIOSK_5"
                ]
              },
              {
                "name": "Grill House",
                "tip": "${'$'}10.22",
                "deviceList": [
                  "KIOSK_4",
                  "KIOSK_2"
                ]
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