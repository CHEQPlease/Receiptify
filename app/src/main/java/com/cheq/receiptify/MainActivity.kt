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
          "orderType": "Counter",
          "totalItems": "Total items: 2",
          "orderNo": "Order# :K10",
          "receiptType": "merchant_split",
          "deviceType": "handheld",
          "timeOfOrder": "May 23, 07:23PM",
          "footerQR": "123456wvweq2512123tqdsasdgasdgasdg",
          "isReprinted": true,
          "splitCount": "SPLIT COUNT: 4",
          "items": [
            {
              "itemName": "Salmon Fry",
              "description": "-- <b>Olive</b> \n  -- Deep Fried Salmon\n  -- ADD Addition 1\n  -- no Nuts\n  -- no Olive Oil\n  -- Substitution 1 SUB\n  -- allergy 1 ALLERGY",
              "quantity": "1x",
              "price": "${'$'}10000.0",
              "strikethrough": false
            },
            {
              "itemName": "Water + Apple Pay",
              "description": "  -- Onions",
              "quantity": "1",
              "price": "${'$'}1.0",
              "strikethrough": true
            }
          ],
          "splits": [
            [
              {
                "key": "SPLIT -1",
                "value": "Card",
                "gap": true
              },
              {
                "key": "Payment type",
                "value": "Card"
              },
              {
                "key": "Card Type",
                "value": "mc"
              },
              {
                "key": "Card #:",
                "value": "541333 **** 9999"
              }
            ],
            [
              {
                "key": "SPLIT -2",
                "value": "Card",
                "gap": true
              },
              {
                "key": "Payment type",
                "value": "Card"
              },
              {
                "key": "Card Type",
                "value": "mc"
              },
              {
                "key": "Card #:",
                "value": "541333 **** 9999"
              }
            ],
            [
              {
                "key": "SPLIT -1",
                "value": "Card",
                "gap": true
              },
              {
                "key": "Payment type",
                "value": "Card"
              },
              {
                "key": "Card Type",
                "value": "mc"
              },
              {
                "key": "Card #:",
                "value": "541333 **** 9999"
              }
            ]
          ],
          "breakdown": [
            {
              "key": "Sub Total",
              "value": "${'$'}21.01"
            },
            {
              "key": "Area Tax",
              "value": "${'$'}1.00"
            },
            {
              "key": "VAT",
              "value": "${'$'}2.10"
            },
            {
              "key": "Customer Fee",
              "value": "${'$'}0.63"
            },
            {
              "key": "Service Fee",
              "value": "${'$'}0.91"
            },
            {
              "key": "Tax",
              "value": "${'$'}0.01"
            },
            {
              "key": "GRAND TOTAL",
              "value": "${'$'}25.66",
              "important": true,
              "gap" : true
            }
          ]
        }                      
        
    """.trimIndent()
        Receiptify.init(this)
        val receiptBitmap = Receiptify.buildReceipt(jsonString)
        binding.ivReceipt.setImageBitmap(receiptBitmap)

    }
}