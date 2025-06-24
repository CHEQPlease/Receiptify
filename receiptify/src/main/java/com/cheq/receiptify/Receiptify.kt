package com.cheq.receiptify


import com.cheq.receiptify.adapter.handheld.HSplitListAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.cheq.receiptify.data.EMVInfoDTO
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
import com.cheq.receiptify.databinding.LayoutPEmvInfoBinding
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNoPosCustomer.visibility = View.GONE
        } else {
            binding.tvTableNoPosCustomer.visibility = View.VISIBLE
            binding.tvTableNoPosCustomer.text = receiptDTO.tableNo
        }
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/

        var placedAt = receiptDTO.placedAt ?: ""

        if(placedAt.isNotEmpty()) {
            binding.tvPairPlacedAtKey.text = "Placed at:"
            binding.tvPairPlacedAtValue.text = placedAt
        } else {
            binding.containerPlacedAtRow.visibility = View.GONE
            binding.tvPairPlacedAtKey.visibility = View.GONE
            binding.tvPairPlacedAtValue.visibility = View.GONE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }
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

        // Add EMV Info if available
        if (receiptDTO.emvInfo != null) {
            addEMVInfo(receiptDTO.emvInfo, binding.includeEmvInfo)
        } else {
            // Hide EMV section when no data is available
            binding.includeEmvInfo.root.visibility = View.GONE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/


        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.tvSplitCount.text = receiptDTO.splitCount

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.rvSplitBreakdown.adapter = PSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }
        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNoHhCustomer.visibility = View.GONE
        }else{
            binding.tvTableNoHhCustomer.visibility = View.VISIBLE
            binding.tvTableNoHhCustomer.text = receiptDTO.tableNo
        }
        binding.tvTotalItems.text = "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/

        // hiding the placed at text if it is null or empty
        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/
        // hiding the placed at text if it is null or empty
        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.tvSplitCount.text = receiptDTO.splitCount
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(!receiptDTO.isRefunded){
            binding.tvRefunded.visibility = View.GONE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.rvSplitBreakdown.adapter = HSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if(!receiptDTO.isRefunded){
            binding.tvRefunded.visibility = View.GONE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvTotalItems.text =
            "${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/

        binding.tvVatAddress.text = receiptDTO.vatAddress
        binding.tvVatId.text = receiptDTO.vatId
        binding.tvPhoneNumber.text = receiptDTO.phoneNumber;
        if (receiptDTO.vatAddress.isNullOrEmpty()) {
            binding.tvVatAddress.visibility = View.GONE
        }
        if (receiptDTO.vatId.isNullOrEmpty()) {
            binding.tvVatId.visibility = View.GONE
        }
        if (receiptDTO.phoneNumber.isNullOrEmpty()) {
            binding.tvPhoneNumber.visibility = View.GONE
        }

        binding.containerGuestNameRow.apply {
            visibility = if (receiptDTO.guestName?.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvPairGuestNameKey.text = receiptDTO.guestName?.key
            binding.tvPairGuestNameKeyValue.text = receiptDTO.guestName?.value
        }


        var placedAt = receiptDTO.placedAt ?: ""

        if(placedAt.isNotEmpty()) {
            binding.tvPairPlacedAtKey.text = "Placed at:"
            binding.tvPairPlacedAtValue.text = placedAt
        } else {
            binding.containerPlacedAtRow.visibility = View.GONE
            binding.tvPairPlacedAtKey.visibility = View.GONE
            binding.tvPairPlacedAtValue.visibility = View.GONE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        if (!receiptDTO.isRefunded) {
            binding.tvRefunded.visibility = View.GONE
        }
        
        // Add EMV Info if available
        if (receiptDTO.emvInfo != null) {
            addEMVInfo(receiptDTO.emvInfo, binding.includeEmvInfo)
        } else {
            // Hide EMV section when no data is available
            binding.includeEmvInfo.root.visibility = View.GONE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2.5f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName

        binding.tvVatAddress.text = receiptDTO.vatAddress
        binding.tvVatId.text = receiptDTO.vatId
        binding.tvPhoneNumber.text = receiptDTO.phoneNumber;
        if (receiptDTO.vatAddress.isNullOrEmpty()) {
            binding.tvVatAddress.visibility = View.GONE
        }
        if (receiptDTO.vatId.isNullOrEmpty()) {
            binding.tvVatId.visibility = View.GONE
        }
        if (receiptDTO.phoneNumber.isNullOrEmpty()) {
            binding.tvPhoneNumber.visibility = View.GONE
        }

        binding.tvOrderNo.text = receiptDTO.orderNo
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        } else {
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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


        var placedAt = receiptDTO.placedAt ?: ""

        if(placedAt.isNotEmpty()) {
            binding.tvPairPlacedAtKey.text = "Placed at:"
            binding.tvPairPlacedAtValue.text = placedAt
        } else {
            binding.tvPairPlacedAtKey.visibility = View.GONE
            binding.tvPairPlacedAtValue.visibility = View.GONE
        }

        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        // Add EMV Info if available
        if (receiptDTO.emvInfo != null) {
            addEMVInfo(receiptDTO.emvInfo, binding.includeEmvInfo)
        } else {
            // Hide EMV section when no data is available
            binding.includeEmvInfo.root.visibility = View.GONE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/

        // Todo: Need to recheck
        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        binding.rvSplitBreakdown.adapter = PSplitListAdapter(receiptDTO.splits)
        binding.rvSplitBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 5f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        } else {
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
        }
        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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

        if (receiptDTO.custMerchantReceiptTopline != null) {
            binding.tvReceiptTopline.text = receiptDTO.custMerchantReceiptTopline
        } else {
            binding.tvReceiptTopline.visibility = View.GONE

            val mm = 2f
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                mm,
                binding.tvBrandName.context.resources.displayMetrics
            ).toInt()

            (binding.tvBrandName.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = px
                binding.tvBrandName.layoutParams = this
            }
        }

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "${receiptDTO.orderNo}"
        binding.tvOrderType.text = "${receiptDTO.orderType}"

        binding.tvServerName.apply {
            visibility = if (receiptDTO.serverName.isNullOrEmpty()) View.GONE else View.VISIBLE
            text = receiptDTO.serverName
        }

        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        binding.tvTotalItems.text =
            receiptDTO.totalItems /* TODO : Move to plural type string resource*/

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        binding.tvCustomerName.text = receiptDTO.customerName
        if (receiptDTO.customerName.isNullOrEmpty()) {
            binding.tvCustomerName.visibility = View.GONE
        }

        binding.tvOrderNo.text = receiptDTO.orderNo

        //This view only visible if suite user enable and suite location available
        if (receiptDTO.suiteLocation.isNullOrEmpty()) {
            binding.tvSuiteLocation.visibility = View.GONE
        } else {
            binding.tvSuiteLocation.visibility = View.VISIBLE
            binding.tvSuiteLocation.text = receiptDTO.suiteLocation
        }

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

        binding.tvAlcoholItemsWarning.text = receiptDTO.alcoholItemWarning
        if (receiptDTO.alcoholItemWarning.isNullOrEmpty()) {
            binding.tvAlcoholItemsWarning.visibility = View.GONE
        }

        binding.containerGuestNameRow.apply {
            visibility = if (receiptDTO.guestName?.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvPairGuestNameKey.text = receiptDTO.guestName?.key
            binding.tvPairGuestNameKeyValue.text = receiptDTO.guestName?.value
        }

        var placedAt = receiptDTO.placedAt ?: ""

        if(placedAt.isNotEmpty()) {
            binding.tvPairPlacedAtKey.text = "Placed at:"
            binding.tvPairPlacedAtValue.text = placedAt
        } else {
            binding.containerPlacedAtRow.visibility = View.GONE
            binding.tvPairPlacedAtKey.visibility = View.GONE
            binding.tvPairPlacedAtValue.visibility = View.GONE
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

        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
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

        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        binding.tvOfflineHeaderMsg.text = receiptDTO.offlineHeaderMsg
        binding.kTvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = receiptDTO.orderNo
        binding.tvTableNo.text = receiptDTO.tableNo
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = HKitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager =
            LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
        }

        //This view only visible if suite user enable and suite location available
        if (receiptDTO.suiteLocation.isNullOrEmpty()) {
            binding.tvSuiteLocation.visibility = View.GONE
        } else {
            binding.tvSuiteLocation.visibility = View.VISIBLE
            binding.tvSuiteLocation.text = receiptDTO.suiteLocation
        }

        if (receiptDTO.orderSubtitle.isNullOrEmpty()) {
            binding.tvOrderSubtitle.visibility = View.GONE
        }

        if (receiptDTO.isReprinted) {
            binding.tvReprinted.visibility = View.VISIBLE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        // 'printStatusText' will be printed to the place of **REPRINTED**
        if (receiptDTO.printStatusText != null) {
            binding.tvReprinted.text = receiptDTO.printStatusText
            binding.tvReprinted.typeface = Typeface.DEFAULT_BOLD
            binding.tvReprinted.visibility = View.VISIBLE
        }

        binding.containerDeviceLabel.apply {
            visibility = if (receiptDTO.deviceLabel.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.tvDeviceLabelValue.text = receiptDTO.deviceLabel
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

            if(receiptDTO.excludeCompanyNameWatermark) {
                binding.tvPoweredBy.visibility = View.GONE
                binding.tvCompanyName.visibility = View.GONE
            } else {
                binding.tvCompanyName.text = receiptDTO.companyName
                binding.tvPoweredBy.visibility = View.VISIBLE
                binding.tvCompanyName.visibility = View.VISIBLE
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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
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
        // hiding the placed at text if it is null or empty
        var placedAt = receiptDTO.placedAt ?: ""
        if(placedAt.isNotEmpty()) {
            binding.tvPlacedAt.text = "Placed at: $placedAt"
        } else {
            binding.tvPlacedAt.visibility = View.GONE
        }

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

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

        if(receiptDTO.excludeCompanyNameWatermark) {
            binding.tvPoweredBy.visibility = View.GONE
            binding.tvCompanyName.visibility = View.GONE
        } else {
            binding.tvCompanyName.text = receiptDTO.companyName
            binding.tvPoweredBy.visibility = View.VISIBLE
            binding.tvCompanyName.visibility = View.VISIBLE
        }

        receipt.measure(
            View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt, TargetPlatform.POS, highQuality = true)
    }

    private fun addEMVInfo(emvInfo: EMVInfoDTO?, emvBinding: LayoutPEmvInfoBinding) {
        if (emvInfo == null) {
            emvBinding.root.visibility = View.GONE
            return
        }

        emvBinding.root.visibility = View.VISIBLE

        /* TODO : Move to string resource to support localization in future*/

        // Set transaction type - hide if null
        if (emvInfo.transactionType.isNullOrEmpty()) {
            emvBinding.tvTransactionType.visibility = View.GONE
        } else {
            emvBinding.tvTransactionType.visibility = View.VISIBLE
            emvBinding.tvTransactionType.text = emvInfo.transactionType
        }

        // Set card brand and last four - hide entire row if cardLastFour is null
        if (emvInfo.cardLastFour.isNullOrEmpty()) {
            emvBinding.containerCardBrandRow.visibility = View.GONE
        } else {
            emvBinding.containerCardBrandRow.visibility = View.VISIBLE
            emvBinding.tvCardBrandLabel.text = "${emvInfo.cardBrand ?: "Card"}:"
            emvBinding.tvCardLastFour.text = emvInfo.cardLastFour
        }

        // Set entry mode - hide entire row if null
        if (emvInfo.entryMode.isNullOrEmpty()) {
            emvBinding.containerEntryModeRow.visibility = View.GONE
        } else {
            emvBinding.containerEntryModeRow.visibility = View.VISIBLE
            emvBinding.tvEntryMode.text = emvInfo.entryMode
        }

        // Set trace number - hide entire row if null
        if (emvInfo.traceNumber.isNullOrEmpty()) {
            emvBinding.containerTraceNumberRow.visibility = View.GONE
        } else {
            emvBinding.containerTraceNumberRow.visibility = View.VISIBLE
            emvBinding.tvTraceNumber.text = emvInfo.traceNumber
        }

        // Set STAN - hide entire row if null
        if (emvInfo.stan.isNullOrEmpty()) {
            emvBinding.containerStanRow.visibility = View.GONE
        } else {
            emvBinding.containerStanRow.visibility = View.VISIBLE
            emvBinding.tvStan.text = emvInfo.stan
        }

        // Set response code - hide entire row if null
        if (emvInfo.responseCode.isNullOrEmpty()) {
            emvBinding.containerResponseCodeRow.visibility = View.GONE
        } else {
            emvBinding.containerResponseCodeRow.visibility = View.VISIBLE
            emvBinding.tvResponseCode.text = emvInfo.responseCode
        }

        // Set auth code - hide entire row if null
        if (emvInfo.authCode.isNullOrEmpty()) {
            emvBinding.containerAuthCodeRow.visibility = View.GONE
        } else {
            emvBinding.containerAuthCodeRow.visibility = View.VISIBLE
            emvBinding.tvAuthCode.text = emvInfo.authCode
        }

        // Set auth mode - hide entire row if null
        if (emvInfo.authMode.isNullOrEmpty()) {
            emvBinding.containerAuthModeRow.visibility = View.GONE
        } else {
            emvBinding.containerAuthModeRow.visibility = View.VISIBLE
            emvBinding.tvAuthMode.text = emvInfo.authMode
        }

        // Set transaction status - show if transactionStatus has a value
        if (!emvInfo.transactionStatus.isNullOrEmpty()) {
            emvBinding.tvApprovedStatus.visibility = View.VISIBLE
            emvBinding.tvApprovedStatus.text = emvInfo.transactionStatus
        } else {
            emvBinding.tvApprovedStatus.visibility = View.GONE
        }

        // Set verification status based on isPinVerified
        when (emvInfo.isPinVerified) {
            true -> {
                emvBinding.tvVerificationStatus.visibility = View.VISIBLE
                emvBinding.tvVerificationStatus.text = "Verified by PIN"
            }
            false -> {
                emvBinding.tvVerificationStatus.visibility = View.VISIBLE
                emvBinding.tvVerificationStatus.text = "No Verification"
            }
            null -> {
                emvBinding.tvVerificationStatus.visibility = View.GONE
            }
        }

        // Show all EMV detail fields based on individual field values, not isPinVerified
        
        // Check if we have any detailed EMV data to show
        val hasDetailedData = !emvInfo.mid.isNullOrEmpty() || !emvInfo.iad.isNullOrEmpty() || 
                             !emvInfo.tid.isNullOrEmpty() || !emvInfo.tsi.isNullOrEmpty() || 
                             !emvInfo.aid.isNullOrEmpty() || !emvInfo.arc.isNullOrEmpty() || 
                             !emvInfo.tvr.isNullOrEmpty()
        
        if (hasDetailedData) {
            emvBinding.containerPinVerifiedDetails.visibility = View.VISIBLE
            
            // MID and IAD Row - show row if at least one field has value
            if (!emvInfo.mid.isNullOrEmpty() || !emvInfo.iad.isNullOrEmpty()) {
                emvBinding.containerMidIadRow.visibility = View.VISIBLE
                
                val hasMid = !emvInfo.mid.isNullOrEmpty()
                val hasIad = !emvInfo.iad.isNullOrEmpty()
                
                if (hasMid && hasIad) {
                    // Both fields present - show side by side with proper alignment
                    emvBinding.containerMidField.visibility = View.VISIBLE
                    emvBinding.containerIadField.visibility = View.VISIBLE
                    emvBinding.tvMid.text = emvInfo.mid
                    emvBinding.tvIad.text = emvInfo.iad
                    // MID stays left-aligned, IAD stays right-aligned (as per XML)
                } else if (hasMid) {
                    // Only MID present - show left-aligned
                    emvBinding.containerMidField.visibility = View.VISIBLE
                    emvBinding.containerIadField.visibility = View.GONE
                    emvBinding.tvMid.text = emvInfo.mid
                } else {
                    // Only IAD present - move to left side and left-align
                    emvBinding.containerMidField.visibility = View.VISIBLE
                    emvBinding.containerIadField.visibility = View.GONE
                    emvBinding.tvMidLabel.text = "IAD: "
                    emvBinding.tvMid.text = emvInfo.iad
                }
            } else {
                emvBinding.containerMidIadRow.visibility = View.GONE
            }
            
            // TID and TSI Row - show row if at least one field has value
            if (!emvInfo.tid.isNullOrEmpty() || !emvInfo.tsi.isNullOrEmpty()) {
                emvBinding.containerTidTsiRow.visibility = View.VISIBLE
                
                val hasTid = !emvInfo.tid.isNullOrEmpty()
                val hasTsi = !emvInfo.tsi.isNullOrEmpty()
                
                if (hasTid && hasTsi) {
                    // Both fields present - show side by side with proper alignment
                    emvBinding.containerTidField.visibility = View.VISIBLE
                    emvBinding.containerTsiField.visibility = View.VISIBLE
                    emvBinding.tvTid.text = emvInfo.tid
                    emvBinding.tvTsi.text = emvInfo.tsi
                    // TID stays left-aligned, TSI stays right-aligned (as per XML)
                } else if (hasTid) {
                    // Only TID present - show left-aligned
                    emvBinding.containerTidField.visibility = View.VISIBLE
                    emvBinding.containerTsiField.visibility = View.GONE
                    emvBinding.tvTid.text = emvInfo.tid
                } else {
                    // Only TSI present - move to left side and left-align
                    emvBinding.containerTidField.visibility = View.VISIBLE
                    emvBinding.containerTsiField.visibility = View.GONE
                    emvBinding.tvTidLabel.text = "TSI: "
                    emvBinding.tvTid.text = emvInfo.tsi
                }
            } else {
                emvBinding.containerTidTsiRow.visibility = View.GONE
            }
            
            // AID and ARC Row - show row if at least one field has value
            if (!emvInfo.aid.isNullOrEmpty() || !emvInfo.arc.isNullOrEmpty()) {
                emvBinding.containerAidArcRow.visibility = View.VISIBLE
                
                val hasAid = !emvInfo.aid.isNullOrEmpty()
                val hasArc = !emvInfo.arc.isNullOrEmpty()
                
                if (hasAid && hasArc) {
                    // Both fields present - show side by side with proper alignment
                    emvBinding.containerAidField.visibility = View.VISIBLE
                    emvBinding.containerArcField.visibility = View.VISIBLE
                    emvBinding.tvAid.text = emvInfo.aid
                    emvBinding.tvArc.text = emvInfo.arc
                    // AID stays left-aligned, ARC stays right-aligned (as per XML)
                } else if (hasAid) {
                    // Only AID present - show left-aligned
                    emvBinding.containerAidField.visibility = View.VISIBLE
                    emvBinding.containerArcField.visibility = View.GONE
                    emvBinding.tvAid.text = emvInfo.aid
                } else {
                    // Only ARC present - move to left side and left-align
                    emvBinding.containerAidField.visibility = View.VISIBLE
                    emvBinding.containerArcField.visibility = View.GONE
                    emvBinding.tvAidLabel.text = "ARC: "
                    emvBinding.tvAid.text = emvInfo.arc
                }
            } else {
                emvBinding.containerAidArcRow.visibility = View.GONE
            }
            
            // TVR Row - show only if it has a value
            if (!emvInfo.tvr.isNullOrEmpty()) {
                emvBinding.containerTvrRow.visibility = View.VISIBLE
                emvBinding.tvTvr.text = emvInfo.tvr
            } else {
                emvBinding.containerTvrRow.visibility = View.GONE
            }
        } else {
            emvBinding.containerPinVerifiedDetails.visibility = View.GONE
        }
    }

}