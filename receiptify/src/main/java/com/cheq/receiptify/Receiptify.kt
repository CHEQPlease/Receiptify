package com.cheq.receiptify


import com.cheq.receiptify.adapter.handheld.HSplitListAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.adapter.handheld.HBreakdownListAdapter
import com.cheq.receiptify.adapter.handheld.HDeviceSalesReportBreakdownAdapter
import com.cheq.receiptify.adapter.handheld.HDeviceSalesReportMetaAdapter
import com.cheq.receiptify.adapter.handheld.HDishListAdapterCustomer
import com.cheq.receiptify.adapter.handheld.HKitchenDishListAdapter
import com.cheq.receiptify.adapter.handheld.HTipsInfoBreakdownListAdapter
import com.cheq.receiptify.adapter.handheld.HTipsPerRevenueCenterListAdapter
import com.cheq.receiptify.adapter.handheld.PSplitListAdapter
import com.cheq.receiptify.adapter.pos.PBreakdownListAdapter
import com.cheq.receiptify.adapter.pos.PDeviceSalesReportBreakdownAdapter
import com.cheq.receiptify.adapter.pos.PDeviceSalesReportMetaAdapter
import com.cheq.receiptify.adapter.pos.PDishListAdapterCustomer
import com.cheq.receiptify.adapter.pos.PGratuityListAdapter
import com.cheq.receiptify.adapter.pos.PKitchenDishListAdapter
import com.cheq.receiptify.adapter.pos.PTipsInfoBreakdownListAdapter
import com.cheq.receiptify.adapter.pos.PTipsPerRevenueCenterListAdapter
import com.cheq.receiptify.data.ReceiptDTO
import com.cheq.receiptify.databinding.LayoutHCustomerKioskReceiptBinding
import com.cheq.receiptify.databinding.LayoutHCustomerPosReceiptBinding
import com.cheq.receiptify.databinding.LayoutHCustomerSplitReceiptBinding
import com.cheq.receiptify.databinding.LayoutHCustomerTotalSplitReceiptBinding
import com.cheq.receiptify.databinding.LayoutHDeviceSalesReportBinding
import com.cheq.receiptify.databinding.LayoutHKitchenReceiptBinding
import com.cheq.receiptify.databinding.LayoutHMerchantReceiptBinding
import com.cheq.receiptify.databinding.LayoutHMerchantSplitReceiptBinding
import com.cheq.receiptify.databinding.LayoutHTipsReceiptBinding
import com.cheq.receiptify.databinding.LayoutPCustomerKioskReceiptBinding
import com.cheq.receiptify.databinding.LayoutPCustomerPosReceiptBinding
import com.cheq.receiptify.databinding.LayoutPCustomerSplitRecepitBinding
import com.cheq.receiptify.databinding.LayoutPCutomerSplitTotalReceiptBinding
import com.cheq.receiptify.databinding.LayoutPDeviceSalesReportBinding
import com.cheq.receiptify.databinding.LayoutPKitchenReceiptBinding
import com.cheq.receiptify.databinding.LayoutPMerchantReceiptBinding
import com.cheq.receiptify.databinding.LayoutPMerchantSplitReceiptBinding
import com.cheq.receiptify.databinding.LayoutPQrPaymentBinding
import com.cheq.receiptify.databinding.LayoutPTipsReceiptBinding
import com.cheq.receiptify.enums.TargetPlatform
import com.cheq.receiptify.enums.ReceiptType
import com.cheq.receiptify.utils.Utils
import java.lang.ref.SoftReference
import kotlin.properties.Delegates


object Receiptify {

    private lateinit var context: SoftReference<Context>
    private var handheldPaperWidth by Delegates.notNull<Int>()
    private var posPaperWidth by Delegates.notNull<Int>()

    fun init(context: Context) {
        this.context = SoftReference(context.applicationContext)

        this.handheldPaperWidth = Utils.convertMmToPixel(40f, context)
        this.posPaperWidth = Utils.convertMmToPixel(75f, context)
    }

    fun buildReceipt(receiptDTOJSON: String): Bitmap? {

        //Throw error if init has not been called
        if (!::context.isInitialized) {
            throw Exception("Receiptify has not been initialized. Please call Receiptify.init(context) before calling buildReceipt()")
        }

        val receiptDTO = Utils.getReceiptDTO(receiptDTOJSON)
        if (receiptDTO != null) {
            val deviceType = receiptDTO.deviceType.uppercase()
            val receiptType = receiptDTO.receiptType.uppercase()

            if (deviceType.uppercase() == TargetPlatform.HANDHELD.name) {

                when (receiptType) {
                    ReceiptType.CUSTOMER_TOTAL_SPLIT.name -> {
                        return buildCustomerTotalSplitReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.CUSTOMER_SPLIT.name -> {
                        return buildCustomerSplitReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.CUSTOMER.name -> {
                        return buildCustomerReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.KIOSK.name -> {
                        return buildKioskReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.MERCHANT.name -> {
                        return buildMerchantReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.MERCHANT_SPLIT.name -> {
                        return buildMerchantSplitReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.KITCHEN.name -> {
                        return buildKitchenReceiptHandHeld(receiptDTO)
                    }

                    ReceiptType.SERVER_TIPS.name -> {
                        return buildTipsReceiptForServerHandheld(receiptDTO)
                    }

                    ReceiptType.DEVICE_SALES_REPORT.name -> {
                        return buildDeviceSalesReportHandheld(receiptDTO)
                    }
                }

            } else if (deviceType.uppercase() == TargetPlatform.POS.name) {

                when (receiptType) {
                    ReceiptType.CUSTOMER.name -> {
                        return buildCustomerReceiptPOS(receiptDTO)
                    }

                    ReceiptType.CUSTOMER_TOTAL_SPLIT.name -> {
                        return buildCustomerTotalSplitReceiptPOS(receiptDTO)
                    }

                    ReceiptType.CUSTOMER_SPLIT.name -> {
                        return buildCustomerSplitReceiptPOS(receiptDTO)
                    }

                    ReceiptType.KIOSK.name -> {
                        return buildKioskReceiptPOS(receiptDTO)
                    }

                    ReceiptType.MERCHANT.name -> {
                        return buildMerchantReceiptPOS(receiptDTO)
                    }

                    ReceiptType.MERCHANT_SPLIT.name -> {
                        return buildMerchantSplitReceiptPOS(receiptDTO)
                    }

                    ReceiptType.KITCHEN.name -> {
                        return buildKitchenReceiptPOS(receiptDTO)
                    }

                    ReceiptType.SERVER_TIPS.name -> {
                        return buildTipsReceiptForServerPOS(receiptDTO)
                    }

                    ReceiptType.QR_PAYMENT.name -> {
                        return buildQRPaymentReceipt(receiptDTO)
                    }

                    ReceiptType.DEVICE_SALES_REPORT.name -> {
                        return buildDeviceSalesReportPOS(receiptDTO)
                    }
                }
            }
        }

        throw IllegalArgumentException("Invalid Device/Receipt type")
    }

    private fun buildCustomerReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPCustomerPosReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerReceiptPos

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName

        binding.tvVatAddress.text = receiptDTO.vatAddress
        binding.tvVatId.text = receiptDTO.vatId
        if (receiptDTO.vatAddress.isNullOrEmpty()) {
            binding.tvVatAddress.visibility = View.GONE
        }
        if (receiptDTO.vatId.isNullOrEmpty()) {
            binding.tvVatId.visibility = View.GONE
        }

        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNoPosCustomer.visibility = View.GONE
        } else {
            binding.tvTableNoPosCustomer.visibility = View.VISIBLE
            binding.tvTableNoPosCustomer.text = receiptDTO.tableNo
        }
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/

        binding.tvGuestName.text = receiptDTO.guestName
        if (receiptDTO.guestName.isNullOrEmpty()) {
            binding.tvGuestName.visibility = View.GONE
        }

        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = PDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        if (receiptDTO.gratuityInfo != null) {
            binding.includeGratuitySection.tvSuggestedGratuity.text = "Suggested Gratuity"
            binding.includeGratuitySection.rvGratuityList.adapter =
                PGratuityListAdapter(receiptDTO.gratuityInfo.gratuityItems)
            binding.includeGratuitySection.rvGratuityList.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
            if (!receiptDTO.gratuityInfo.isSignatureNeeded) {
                binding.includeGratuitySection.containerSignature.visibility = View.GONE
            }

            if (!receiptDTO.gratuityInfo.isCustomTip) {
                binding.includeGratuitySection.layoutCustomTip.visibility = View.GONE
            }
        } else {
            binding.includeGratuitySection.root.visibility = View.GONE
        }

        if (!receiptDTO.isRefunded) {
            binding.tvRefunded.visibility = View.GONE
        }

        if (receiptDTO.supportInfo?.isNotEmpty() == true) {
            binding.tvSupportInfo.text = receiptDTO.supportInfo
            binding.tvSupportInfo.visibility = View.VISIBLE
        } else {
            binding.tvSupportInfo.visibility = View.GONE
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                posPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt, targetPlatform = TargetPlatform.POS, highQuality = true)
    }

    private fun buildCustomerTotalSplitReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPCutomerSplitTotalReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerTotalSplitReceiptPos

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvSplitCount.text = receiptDTO.splitCount

        binding.rvDishes.adapter = PDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        if (!receiptDTO.isRefunded) {
            binding.tvRefunded.visibility = View.GONE
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                posPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt, TargetPlatform.POS, highQuality = true)
    }

    private fun buildCustomerSplitReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {

        val binding = LayoutPCustomerSplitRecepitBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerSplitReceiptPos
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder

        binding.rvSplitBreakdown.adapter = PSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if (!receiptDTO.isRefunded) {
            binding.tvRefunded.visibility = View.GONE
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                posPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt, TargetPlatform.POS, highQuality = true)
    }


    private fun buildCustomerReceiptHandheld(receiptDTO: ReceiptDTO) : Bitmap {

        val binding = LayoutHCustomerPosReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerReceiptPos
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNoHhCustomer.visibility = View.GONE
        }else{
            binding.tvTableNoHhCustomer.visibility = View.VISIBLE
            binding.tvTableNoHhCustomer.text = receiptDTO.tableNo
        }
        binding.tvTotalItems.text = "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(receiptDTO.supportInfo?.isNotEmpty() == true){
            binding.tvSupportInfo.text = receiptDTO.supportInfo
            binding.tvSupportInfo.visibility = View.VISIBLE
        }else{
            binding.tvSupportInfo.visibility = View.GONE
        }

        if(!receiptDTO.isRefunded){
            binding.tvRefunded.visibility = View.GONE
        }

        customerReceipt.measure( View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)


        return Utils.generateBitmap(customerReceipt, TargetPlatform.HANDHELD, highQuality = true)

    }

    private fun buildCustomerTotalSplitReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {

        val binding =
            LayoutHCustomerTotalSplitReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerTotalSplitReceipt
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvSplitCount.text = receiptDTO.splitCount
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(!receiptDTO.isRefunded){
            binding.tvRefunded.visibility = View.GONE
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)


        return Utils.generateBitmap(customerReceipt, targetPlatform = TargetPlatform.POS, highQuality = true)
    }

    private fun buildCustomerSplitReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {

        val binding = LayoutHCustomerSplitReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerTotalSplitReceipt
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder

        binding.rvSplitBreakdown.adapter = HSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(!receiptDTO.isRefunded){
            binding.tvRefunded.visibility = View.GONE
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)


        return Utils.generateBitmap(customerReceipt, targetPlatform = TargetPlatform.POS, highQuality = true)

    }


    private fun buildKioskReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutCustomerReceiptKiosk
        val context = context.get()!!


        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType

        if (receiptDTO.supportInfo?.isNotEmpty() == true) {
            binding.tvSupportInfo.text = receiptDTO.supportInfo
            binding.tvSupportInfo.visibility = View.VISIBLE
        } else {
            binding.tvSupportInfo.visibility = View.GONE
        }

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)


        return Utils.generateBitmap(receipt, TargetPlatform.HANDHELD)
    }


    private fun buildKioskReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = PDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType

        if (receiptDTO.supportInfo?.isNotEmpty() == true) {
            var text = receiptDTO.supportInfo
            binding.tvSupportInfo.visibility = View.VISIBLE
            binding.tvSupportInfo.text = text
        } else {
            binding.tvSupportInfo.visibility = View.GONE
        }


        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS, highQuality = true)
    }


    private fun buildMerchantReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPMerchantReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutMerchantReceiptBinding

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        } else {
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }


        if (receiptDTO.gratuityInfo != null) {
            binding.includeGratuitySection.tvSuggestedGratuity.text = "Suggested Gratuity"
            binding.includeGratuitySection.rvGratuityList.adapter =
                PGratuityListAdapter(receiptDTO.gratuityInfo.gratuityItems)
            binding.includeGratuitySection.rvGratuityList.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
            if (!receiptDTO.gratuityInfo.isSignatureNeeded) {
                binding.includeGratuitySection.containerSignature.visibility = View.GONE
            }

        } else {
            binding.includeGratuitySection.root.visibility = View.GONE
        }

        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }
        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS, highQuality = true)

    }

    private fun buildMerchantSplitReceiptPOS(receiptDTO: ReceiptDTO): Bitmap {

        val binding = LayoutPMerchantSplitReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutMerchantSplitReceiptPos
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder

        binding.rvSplitBreakdown.adapter = PSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                posPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt, targetPlatform = TargetPlatform.POS, highQuality = true)
    }


    private fun buildMerchantReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHMerchantReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutMerchantReceiptBinding

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        } else {
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.HANDHELD)
    }

    private fun buildMerchantSplitReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {

        val binding = LayoutHMerchantSplitReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutMerchantSplitReceipt
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder

        binding.rvSplitBreakdown.adapter = HSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        customerReceipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)


        return Utils.generateBitmap(customerReceipt, targetPlatform = TargetPlatform.POS, highQuality = true)

    }

    private fun buildKitchenReceiptPOS(receiptDTO: ReceiptDTO): Bitmap? {
        val binding = LayoutPKitchenReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutKitchenReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvOfflineHeaderMsg.text = receiptDTO.offlineHeaderMsg
        binding.kTvBrandName.text = receiptDTO.brandName
        binding.tvTableNo.text = receiptDTO.tableNo
        binding.tvAlcoholItemsWarning.text = receiptDTO.alcoholItemWarning
        binding.tvGuestName.text = receiptDTO.guestName
        binding.tvCustomerName.text = receiptDTO.customerName
        binding.tvOrderNo.text = receiptDTO.orderNo
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = PKitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        if (receiptDTO.orderSubtitle.isNullOrEmpty()) {
            binding.tvOrderSubtitle.visibility = View.GONE
        }

        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
        }

        if (receiptDTO.alcoholItemWarning.isNullOrEmpty()) {
            binding.tvAlcoholItemsWarning.visibility = View.GONE
        }

        if (receiptDTO.guestName.isNullOrEmpty()) {
            binding.tvGuestName.visibility = View.GONE
        }

        if (receiptDTO.customerName.isNullOrEmpty()) {
            binding.tvCustomerName.visibility = View.GONE
        }

        if (receiptDTO.isReprinted) {
            binding.tvReprinted.visibility = View.VISIBLE
        }

        if (receiptDTO.printStatusText != null) {
            binding.tvReprinted.text = receiptDTO.printStatusText
            binding.tvReprinted.typeface = Typeface.DEFAULT_BOLD
            binding.tvReprinted.visibility = View.VISIBLE
        }

        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }
        receiptDTO.footerQR?.let {
            binding.ivFooterQrCode.apply {
                visibility = if (receiptDTO.footerQR.isNullOrEmpty()) View.GONE else View.VISIBLE
                setImageBitmap(Utils.generateQRBitmap(context, receiptDTO.footerQR, 40f, 60f))
            }
        }
        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS, highQuality = false)

    }

    private fun buildKitchenReceiptHandHeld(receiptDTO: ReceiptDTO): Bitmap? {
        val binding = LayoutHKitchenReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutKitchenReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvOfflineHeaderMsg.text = receiptDTO.offlineHeaderMsg
        binding.kTvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        binding.tvTableNo.text = receiptDTO.tableNo
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = HKitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
        }

        if (receiptDTO.orderSubtitle.isNullOrEmpty()) {
            binding.tvOrderSubtitle.visibility = View.GONE
        }

        if (receiptDTO.isReprinted) {
            binding.tvReprinted.visibility = View.VISIBLE
        }

        // 'printStatusText' will be printed to the place of **REPRINTED**
        if (receiptDTO.printStatusText != null) {
            binding.tvReprinted.text = receiptDTO.printStatusText
            binding.tvReprinted.typeface = Typeface.DEFAULT_BOLD
            binding.tvReprinted.visibility = View.VISIBLE
        }

        binding.containerDeviceName.apply {
            visibility = if (receiptDTO.deviceName.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceNameValue.text = receiptDTO.deviceName
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        receiptDTO.footerQR?.let {
            binding.ivFooterQrCode.apply {
                visibility = if (receiptDTO.footerQR.isNullOrEmpty()) View.GONE else View.VISIBLE
                setImageBitmap(Utils.generateQRBitmap(context, receiptDTO.footerQR, 40f, 60f))
            }
        }
        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.HANDHELD)
    }

    private fun buildTipsReceiptForServerPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPTipsReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutTipsReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvServerName.text = receiptDTO.serverTipInfo.serverName
        binding.tvServerId.text = receiptDTO.serverTipInfo.serverId
        if (receiptDTO.serverTipInfo.serverId.isNullOrEmpty()) {
            binding.tvServerId.visibility = View.GONE
        }
        binding.tvTotalTipsValue.apply {
            text = receiptDTO.serverTipInfo.totalTip
            visibility = if (receiptDTO.serverTipInfo.totalTip == null) View.GONE else View.VISIBLE
            binding.tvTotalTips.visibility = visibility
        }
        binding.tvTotalNetSalesValue.apply {
            text = receiptDTO.serverTipInfo.totalNetSales
            visibility =
                if (receiptDTO.serverTipInfo.totalNetSales == null) View.GONE else View.VISIBLE
            binding.tvTotalNetSales.visibility = visibility
        }
        binding.tvCashValue.apply {
            text = receiptDTO.serverTipInfo.cash
            visibility =
                if (receiptDTO.serverTipInfo.cash.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvCash.visibility = visibility
        }
        binding.tvOtherPaymentTypesValue.apply {
            text = receiptDTO.serverTipInfo.otherPayment
            visibility =
                if (receiptDTO.serverTipInfo.otherPayment.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvOtherPaymentTypes.visibility = visibility
        }

        binding.rvTipsInfoBreakdown.apply {
            adapter = receiptDTO.serverTipInfo.tipsInfoBreakdown?.let {
                PTipsInfoBreakdownListAdapter(
                    it
                )
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            visibility =
                if (receiptDTO.serverTipInfo.tipsInfoBreakdown.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        binding.rvSalesByRevenueCenter.apply {
            adapter = receiptDTO.serverTipInfo.tipPerRevenueCenter?.let {
                PTipsPerRevenueCenterListAdapter(
                    it
                )
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            visibility =
                if (receiptDTO.serverTipInfo.tipPerRevenueCenter.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        val shouldHideDividers =
            receiptDTO.serverTipInfo.totalTip == null &&
                    receiptDTO.serverTipInfo.totalNetSales == null &&
                    receiptDTO.serverTipInfo.cash == null &&
                    receiptDTO.serverTipInfo.otherPayment == null &&
                    receiptDTO.serverTipInfo.tipPerRevenueCenter.isNullOrEmpty()


        binding.ivDivider3.visibility = if (shouldHideDividers) View.GONE else View.VISIBLE
        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS)
    }

    private fun buildTipsReceiptForServerHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHTipsReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutTipsReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvServerName.apply {
            text = receiptDTO.serverTipInfo.serverName
            visibility =
                if (receiptDTO.serverTipInfo.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        binding.tvServerId.apply {
            text = receiptDTO.serverTipInfo.serverId
            visibility =
                if (receiptDTO.serverTipInfo.serverId.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        binding.tvTotalTipsValue.apply {
            text = receiptDTO.serverTipInfo.totalTip
            visibility = if (receiptDTO.serverTipInfo.totalTip == null) View.GONE else View.VISIBLE
            binding.tvTotalTips.visibility = visibility
        }

        binding.tvTotalNetSalesValue.apply {
            text = receiptDTO.serverTipInfo.totalNetSales
            visibility =
                if (receiptDTO.serverTipInfo.totalNetSales == null) View.GONE else View.VISIBLE
            binding.tvTotalNetSales.visibility = visibility
        }

        binding.tvCashValue.apply {
            text = receiptDTO.serverTipInfo.cash
            visibility = if (receiptDTO.serverTipInfo.cash == null) View.GONE else View.VISIBLE
            binding.tvCash.visibility = visibility
        }

        binding.tvOtherPaymentTypesValue.apply {
            text = receiptDTO.serverTipInfo.otherPayment
            visibility =
                if (receiptDTO.serverTipInfo.otherPayment == null) View.GONE else View.VISIBLE
            binding.tvOtherPaymentTypes.visibility = visibility
        }

        binding.rvTipsInfoBreakdown.apply {
            adapter = receiptDTO.serverTipInfo.tipsInfoBreakdown?.let {
                HTipsInfoBreakdownListAdapter(
                    it
                )
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            visibility =
                if (receiptDTO.serverTipInfo.tipsInfoBreakdown.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        binding.rvSalesByRevenueCenter.apply {
            adapter = receiptDTO.serverTipInfo.tipPerRevenueCenter?.let {
                HTipsPerRevenueCenterListAdapter(
                    it
                )
            }
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            visibility =
                if (receiptDTO.serverTipInfo.tipPerRevenueCenter.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        val shouldHideDividers =
            receiptDTO.serverTipInfo.totalTip == null &&
                    receiptDTO.serverTipInfo.totalNetSales == null &&
                    receiptDTO.serverTipInfo.cash == null &&
                    receiptDTO.serverTipInfo.otherPayment == null &&
                    receiptDTO.serverTipInfo.tipPerRevenueCenter.isNullOrEmpty()

        binding.ivDivider2.visibility = if (shouldHideDividers) View.GONE else View.VISIBLE

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.HANDHELD)
    }

    private fun buildQRPaymentReceipt(receiptDTO: ReceiptDTO): Bitmap? {

        val binding = LayoutPQrPaymentBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutQrPayment

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        binding.tvTableNo.text = receiptDTO.tableNo
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.ivPaymentQr.setImageBitmap(context.get()
            ?.let { Utils.generateQRBitmap(it, receiptDTO.paymentQR, 30f, 40f) })

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS)
    }

    private fun buildDeviceSalesReportHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHDeviceSalesReportBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutDeviceSalesReport

        binding.tvPartnerName.text = receiptDTO.deviceSalesReport.header

        if (receiptDTO.deviceSalesReport.headerMeta?.isNotEmpty() == true) {
            binding.rvDevicesSalesHeaderMeta.adapter =
                HDeviceSalesReportMetaAdapter(receiptDTO.deviceSalesReport.headerMeta)
            binding.rvDevicesSalesHeaderMeta.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        }

        if (receiptDTO.deviceSalesReport.breakdown?.isNotEmpty() == true) {
            binding.rvDeviceSalesPaymentBreakdown.adapter =
                HDeviceSalesReportBreakdownAdapter(receiptDTO.deviceSalesReport.breakdown)
            binding.rvDeviceSalesPaymentBreakdown.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        }

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(
                handheldPaperWidth,
                View.MeasureSpec.EXACTLY
            ), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.HANDHELD, highQuality = true)
    }


    private fun buildDeviceSalesReportPOS(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutPDeviceSalesReportBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutDeviceSalesReport

        binding.tvPartnerName.text = receiptDTO.deviceSalesReport.header

        if (receiptDTO.deviceSalesReport.headerMeta?.isNotEmpty() == true) {
            binding.rvDeviceSalesHeaderMeta.adapter =
                PDeviceSalesReportMetaAdapter(receiptDTO.deviceSalesReport.headerMeta)
            binding.rvDeviceSalesHeaderMeta.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        }

        if (receiptDTO.deviceSalesReport.breakdown?.isNotEmpty() == true) {
            binding.rvDeviceSalesPaymentBreakdown.adapter =
                PDeviceSalesReportBreakdownAdapter(receiptDTO.deviceSalesReport.breakdown)
            binding.rvDeviceSalesPaymentBreakdown.layoutManager =
                LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        }

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS, highQuality = true)
    }


}