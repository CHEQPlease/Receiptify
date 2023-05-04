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
implementation 'com.github.CHEQPlease:Receiptify:1.0.0'
```

**Generating Receipt**

**Step 1**: Initiate the SDK inside your application

    Receptify.init(context);

**Step 2:** Prepare JSON DTO for your desired receipt. A demo dto is provided below


     val demoDTOJSON = """
     {
       "brandName":"CHEQ Diner",
       "orderType":"Self-Order",
       "orderSubtitle":"Kiosk-Order",
       "totalItems":"2",
       "orderNo":"K10",
       "tableNo":"234",
       "receiptType":"customer",
       "deviceType":"pos",
       "timeOfOrder":"Placed at : 01/12/2023 03:57 AM AKST",
       "items":[
          {
             "itemName":"Salmon Fry",
             "description":"  -- Olive\n  -- Deep Fried Salmon\n  -- ADD Addition 1\n  -- no Nuts\n  -- no Olive Oil\n  -- Substitution 1 SUB\n  -- allergy 1 ALLERGY\n",
             "quantity":"231",
             "price":"${'$'}10000.0",
             "strikethrough":false
          },
          {
             "itemName":"Water + Apple Pay",
             "description":"  -- Onions\n",
             "quantity":"1",
             "price":"${'$'}1.0",
             "strikethrough":true
          }
       ],
       "breakdown":[
          {
             "key":"Payment Type",
             "value":"Card"
          },
          {
             "key":"Card Type",
             "value":"mc"
          },
          {
             "key":"Card #:",
             "value":"541333 **** 9999"
          },
          {
             "key":"Card Entry",
             "value":"CONTACTLESS"
          },
          {
             "key":"",
             "value":""
          },
          {
             "key":"Sub Total",
             "value":"${'$'}21.01"
          },
          {
             "key":"Area Tax",
             "value":"${'$'}1.00"
          },
          {
             "key":"VAT",
             "value":"${'$'}2.10"
          },
          {
             "key":"Customer Fee",
             "value":"${'$'}0.63"
          },
          {
             "key":"Service Fee",
             "value":"${'$'}0.91"
          },
          {
             "key":"Tax",
             "value":"${'$'}0.01"
          },
          {
             "key":"GRAND TOTAL",
             "value":"${'$'}25.66",
             "important":true
          }
       ]
    }""".trimIndent()

**Step 3**: Send the JSON String to receiptify SDK to generate receipt bitmap

    val receiptBitmap = Receiptify.buildReceipt(demoDTOJSON)


**Note** :

**Supported values for "receiptType" :**
Customer,
Merchant,
Kitchen,
Kiosk

**Supported values for "deviceType":**
POS,
Handheld
 
