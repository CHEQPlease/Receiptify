## Receiptify [![](https://jitpack.io/v/CHEQPlease/Receiptify.svg)](https://jitpack.io/#CHEQPlease/Receiptify)
An Android Library for CHEQ apps to generate receipt bitmaps.

**How to use ?**

Add it in your root build.gradle at the end of repositories:

```css
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency

```css
implementation 'com.github.CHEQPlease:Receiptify:1.4.5'
```

**Generating Receipt**

**Step 1**: Initiate the SDK inside your application

    Receptify.init(context);

**Step 2:** Prepare JSON DTO for your desired receipt. A demo dto is provided below


      val jsonString = """
            {
              "brandName": "CHEQ Diner",
              "orderType": "Self-Order",
             // "orderSubtitle": "Kiosk-Order",
              "totalItems": "2",
              "orderNo": "K10",
              "tableNo": "Table #: 234",
              "customerName": "Customer name : Sheikh Faisal Khelzi",
              "receiptType": "kitchen",
              "deviceType": "pos",
              "timeOfOrder": "Placed at : 01/12/2023 03:57 AM AKST",
              "paymentQRLink": "https://www.google.com",
              "isRefunded" : true,
              "isReprinted" : false,
              "offlineHeaderMsg" : "Please provide this copy to an attendant",
              "supportInfo" : "For support, please contact 1-800-123-4567",
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
              "breakdown": [
                {
                  "key": "Payment Type",
                  "value": "Card",
                  "important": null
                },
                {
                  "key": "Card Type",
                  "value": "mc"
                },
                {
                  "key": "Card #:",
                  "value": "541333 **** 9999"
                },
                {
                  "key": "Card Entry",
                  "value": "CONTACTLESS"
                },
                {
                  "key": "",
                  "value": ""
                },
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
                  "important": true
                }
              ],
              "gratuityInfo": {
                "gratuityItems": [
                  {
                    "tip": "❏ ${'$'}1.0 tip = ${'$'}1.00 ,",
                    "total": "TOTAL =  ${'$'}93.00"
                  },
                  {
                    "tip": "❏ ${'$'}3.0 tip = ${'$'}3.00 ,",
                    "total": "TOTAL =  ${'$'}93.00"
                  },
                  {
                    "tip": "❏ ${'$'}100.0 tip = ${'$'}5.00 ,",
                    "total": "TOTAL =  ${'$'}95.00"
                  }
                ],
                "isSignatureNeeded": true,
                "isCustomTip" : false
              },
              "serverTipInfo": {
                "serverName": "Shorol",
                "serverId": "123333330022",
                "totalTip": " ${'$'}200.19",
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

**Step 3**: Send the JSON String to receiptify SDK to generate receipt bitmap

    val receiptBitmap = Receiptify.buildReceipt(demoDTOJSON)


**Note** :

**Supported values for "receiptType" :**
Customer,
Merchant,
Kitchen,
Kiosk,
ServerTips

**Supported values for "deviceType":**
POS,
Handheld
 
