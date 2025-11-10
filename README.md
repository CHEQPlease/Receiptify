## Receiptify [![](https://jitpack.io/v/CHEQPlease/Receiptify.svg)](https://jitpack.io/#CHEQPlease/Receiptify)
An Android library to generate high-quality receipt bitmaps for CHEQ apps across multiple use cases and devices (POS and Handheld).

### Highlights
- Multiple receipt types: Customer, Merchant, Kitchen, Kiosk, Splits, QR Payment, Server Tips, Device Sales Report, Time Sheet
- POS and Handheld layouts with accurate millimeter-based sizing
- JSON-in, Bitmap-out API with optional EMV, QR and gratuity sections

---

## Installation

Add JitPack to your root build.gradle (or repositories block):

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:

```gradle
implementation 'com.github.CHEQPlease:Receiptify:1.4.5'
```

---

## Quick Start

1) Initialize once (e.g., in Application or before first use):

```kotlin
Receiptify.init(context)
```

2) Build a receipt bitmap from a JSON string:

```kotlin
val receiptBitmap = Receiptify.buildReceipt(jsonString)
```

- deviceType: `pos` or `handheld` (case-insensitive)
- receiptType: see Supported Receipt Types below


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

**Step 3**: Send the JSON String to Receiptify to generate the bitmap

```kotlin
val receiptBitmap = Receiptify.buildReceipt(jsonString)
```


**Supported values for receiptType**

Customer, CustomerSplit, CustomerTotalSplit, Merchant, MerchantSplit, Kitchen, Kiosk, ServerTips, QRPayment, DeviceSalesReport, Time_Sheet

**Supported values for deviceType**

POS, Handheld

---

## Common JSON Fields

These fields are commonly used across receipts (only include what you need):

```json
{
  "companyName": "CANTALOUPE",
  "brandName": "CHEQ Diner",
  "receiptType": "customer",
  "deviceType": "pos",
  "orderNo": "K10",
  "orderType": "Counter",
  "orderSubtitle": "Kiosk-Order",
  "tableNo": "Table #: 234",
  "customerName": "John Doe",
  "placedAt": "May 23, 07:23PM",
  "timeOfOrder": "Placed at : 01/12/2023 03:57 AM AKST",
  "phoneNumber": "(123) 4567890",
  "vatAddress": "1234 Street, City, Country",
  "vatId": "VAT123456789",
  "supportInfo": "For support, contact help@cheq.io",
  "excludeCompanyNameWatermark": false
}
```

### Items (line items)
```json
"items": [
  { "itemName": "Salmon Fry", "description": "-- Onions", "quantity": "1x", "price": "$12.00", "strikethrough": false }
]
```

### Breakdown (key-value rows with optional emphasis)
```json
"breakdown": [
  { "key": "Sub Total", "value": "$21.01" },
  { "key": "GRAND TOTAL", "value": "$25.66", "important": true, "gap": true }
]
```

### EMV Info (POS receipts)
If present, EMV info renders automatically.
```json
"emvInfo": {
  "transactionType": "Sale",
  "cardBrand": "Visa",
  "cardLastFour": "************0043",
  "entryMode": "Chip",
  "traceNumber": "12345",
  "stan": "425074",
  "responseCode": "00",
  "authCode": "208961",
  "authMode": "Issuer",
  "transactionStatus": "APPROVED",
  "mid": "99901114",
  "tid": "01249102",
  "tsi": "F800",
  "aid": "A0000000031010",
  "arc": "00",
  "tvr": "0080008000",
  "isPinVerified": true
}
```

---

## Use-Case Examples

### Customer (POS)
```json
{
  "brandName": "CHEQ Diner",
  "companyName": "CANTALOUPE",
  "deviceType": "pos",
  "receiptType": "customer",
  "orderNo": "Order#: K10",
  "totalItems": "Total items: 2",
  "items": [
    { "itemName": "Salmon Fry", "quantity": "1x", "price": "$12.00" },
    { "itemName": "Water", "quantity": "1x", "price": "$1.00" }
  ],
  "breakdown": [
    { "key": "Sub Total", "value": "$13.00" },
    { "key": "GRAND TOTAL", "value": "$13.00", "important": true }
  ]
}
```

### Merchant (POS) with EMV
```json
{
  "brandName": "CHEQ Diner",
  "companyName": "CANTALOUPE",
  "deviceType": "pos",
  "receiptType": "merchant",
  "orderNo": "K10",
  "breakdown": [ { "key": "GRAND TOTAL", "value": "$25.66", "important": true } ],
  "emvInfo": {
    "transactionType": "Sale",
    "cardBrand": "Visa",
    "cardLastFour": "************0043",
    "entryMode": "Chip",
    "authCode": "208961",
    "transactionStatus": "APPROVED"
  }
}
```

### Kitchen (POS)
```json
{
  "brandName": "CHEQ Diner",
  "deviceType": "pos",
  "receiptType": "kitchen",
  "tableNo": "Table #: 234",
  "items": [
    { "itemName": "Salmon Fry", "description": "-- no Pepper", "quantity": "1x" }
  ]
}
```

### Customer Split / Total Split (POS)
```json
{
  "brandName": "CHEQ Diner",
  "deviceType": "pos",
  "receiptType": "customer_split",
  "splits": [
    [ { "key": "SPLIT -1", "value": "Card", "gap": true }, { "key": "Card Type", "value": "mc" } ],
    [ { "key": "SPLIT -2", "value": "Card", "gap": true }, { "key": "Card Type", "value": "visa" } ]
  ]
}
```

### Server Tips (POS)
```json
{
  "deviceType": "pos",
  "receiptType": "server_tips",
  "companyName": "CANTALOUPE",
  "serverTipInfo": {
    "serverName": "Alex",
    "serverId": "12345",
    "totalTip": "$200.19",
    "tipsInfoBreakdown": [ { "key": "Placed at", "value": "24 Apr 2023" } ],
    "tipPerRevenueCenter": [
      { "name": "Obie's", "tip": "$120", "deviceList": ["KIOSK_1", "KIOSK_2"] }
    ]
  }
}
```

### QR Payment (POS)
```json
{
  "brandName": "CHEQ Diner",
  "deviceType": "pos",
  "receiptType": "qr_payment",
  "paymentQR": "https://example.com/qr-token"
}
```

### Device Sales Report
POS: `device_sales_report` (Handheld also supported). Provide the `deviceSalesReport` object (`header`, `headerMeta`, `breakdown`).

### Time Sheet (POS)
Use grouped `timeSheetInfo` (preferred). Flat fields are still accepted for backward compatibility and will be used as fallback.
```json
{
  "companyName": "CANTALOUPE",
  "brandName": "CHEQ Diner",
  "deviceType": "pos",
  "receiptType": "time_sheet",
  "timeSheetInfo": {
    "employeeName": "John Smith",
    "date": "Tuesday, August 26, 2025",
    "totalWorkTime": "08:15",
    "breakTime": "00:45",
    "totalTips": "$75.00"
  }
}
```

---

## Handheld vs POS
- POS layouts use 75mm width; handheld uses 40mm. The library converts mm to px for correct printer sizing.
- Some sections (e.g., EMV) are shown only on POS.

---

## Tips & Gratuity
Provide `gratuityInfo` to show suggested tip amounts and an optional signature section (`isSignatureNeeded`).

---

## QR Codes
Provide `paymentQR` (transaction QR) or `footerQR` (footer-only QR). The library generates the QR bitmap automatically.

---

## Troubleshooting
- Build artifacts missing (IDE redirect files): `./gradlew :app:clean :app:assembleDebug`
- Nothing renders: ensure `Receiptify.init(context)` was called and JSON includes valid `deviceType` and `receiptType`
- Sections not visible: optional sections hide automatically when empty

---

## Versioning & Releases
- Distributed via JitPack. Use the badge version at the top or a tagged release.
- Changes aim for backward compatibility (e.g., `timeSheetInfo` with flat-field fallback).

---

## License
Copyright © CHEQ. All rights reserved.

### Time Sheet Receipt (POS)

To generate a Time Sheet receipt for POS, set `receiptType` to `time_sheet` and group related fields inside `timeSheetInfo`. Flat fields (`employeeName`, `timeSheetDate`, `totalWorkTime`, `breakTime`, `totalTips`) are still supported for backward compatibility but `timeSheetInfo` takes precedence when present.

```json
{
  "companyName": "CANTALOUPE",
  "brandName": "CHEQ Diner",
  "deviceType": "pos",
  "receiptType": "time_sheet",
  "timeSheetInfo": {
    "employeeName": "John Smith",
    "date": "Tuesday, August 26, 2025",
    "totalWorkTime": "08:15",
    "breakTime": "00:45",
    "totalTips": "$75.00"
  }
}
```

This renders a compact time sheet receipt aligned with the POS layouts used by the library.
