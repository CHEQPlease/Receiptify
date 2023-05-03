package com.cheq.receiptify


import android.content.Context
import android.graphics.Bitmap
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.adapter.handheld.HBreakdownListAdapter
import com.cheq.receiptify.adapter.handheld.HDishListAdapterCustomer
import com.cheq.receiptify.adapter.handheld.HKitchenDishListAdapter
import com.cheq.receiptify.adapter.pos.PBreakdownListAdapter
import com.cheq.receiptify.adapter.pos.PDishListAdapterCustomer
import com.cheq.receiptify.adapter.pos.PKitchenDishListAdapter
import com.cheq.receiptify.adapter.pos.PTipsInfoBreakdownListAdapter
import com.cheq.receiptify.data.DeviceType
import com.cheq.receiptify.data.ReceiptDTO
import com.cheq.receiptify.data.ReceiptType
import com.cheq.receiptify.databinding.*
import com.cheq.receiptify.databinding.LayoutPCustomerPosReceiptBinding
import com.cheq.receiptify.utils.Utils
import java.lang.IllegalArgumentException
import java.lang.ref.SoftReference
import kotlin.properties.Delegates


object Receiptify  {


    private lateinit var context: SoftReference<Context>
    private var handheldPaperWidth by Delegates.notNull<Int>()
    private var posPaperWidth by Delegates.notNull<Int>()

    fun init(context: Context){
        this.context = SoftReference(context.applicationContext)
        this.handheldPaperWidth = Utils.convertMmToPixel(58f, context)
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

            if (deviceType.uppercase() == DeviceType.HANDHELD.name) {
                when (receiptType) {
                    ReceiptType.CUSTOMER.name -> {
                        return buildCustomerReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.KIOSK.name -> {
                        return buildKioskReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.MERCHANT.name -> {
                        return buildMerchantReceiptHandheld(receiptDTO)
                    }

                    ReceiptType.KITCHEN.name -> {
                        return buildKitchenReceiptHandHeld(receiptDTO)
                    }
                }

            } else if (deviceType.uppercase() == DeviceType.POS.name) {
                when (receiptType) {
                    ReceiptType.CUSTOMER.name -> {
                        return buildCustomerReceiptPOS(receiptDTO)
                    }

                    ReceiptType.KIOSK.name -> {
                        return buildKioskReceiptPOS(receiptDTO)
                    }

                    ReceiptType.MERCHANT.name -> {
                        return buildMerchantReceiptPOS(receiptDTO)
                    }

                    ReceiptType.KITCHEN.name -> {
                        return buildKitchenReceiptPOS(receiptDTO)
                    }

                    ReceiptType.SERVER_TIP.name -> {
                        return buildTipsReceiptForServer(receiptDTO)
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
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = PDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        customerReceipt.measure( View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt)
    }

    private fun buildCustomerReceiptHandheld(receiptDTO: ReceiptDTO) : Bitmap {

        val binding = LayoutHCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val customerReceipt = binding.layoutCustomerReceiptKiosk
        val context = context.get()!!

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        customerReceipt.measure( View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt)

    }

    private fun buildKioskReceiptHandheld(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutHCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutCustomerReceiptKiosk
        val context = context.get()!!


        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = HDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType
        receipt.measure( View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)


        return Utils.generateBitmap(receipt)
    }


    private fun buildKioskReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutPCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = PDishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType
        receipt.measure( View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)
    }


    private fun buildMerchantReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutPMerchantReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutMerchantReceiptBinding

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        }else{
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvBreakdown.adapter = PBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        receipt.measure( View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)

    }

    private fun buildMerchantReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHMerchantReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutMerchantReceiptBinding

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        if (receiptDTO.tableNo.isNullOrEmpty()) {
            binding.tvTableNo.visibility = View.GONE
            binding.tvOrderNo.gravity = Gravity.CENTER
        }else{
            binding.tvTableNo.text = receiptDTO.tableNo
            binding.tvOrderNo.gravity = Gravity.END
        }
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvBreakdown.adapter = HBreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        receipt.measure( View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)
    }


    private fun buildKitchenReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap? {
        val binding = LayoutPKitchenReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutKitchenReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvOrderNo.text = "Order #: ${receiptDTO.orderNo}"
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = PKitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        if(receiptDTO.orderSubtitle.isNullOrEmpty()){
            binding.tvOrderSubtitle.visibility = View.GONE
        }
        receipt.measure( View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)

    }

    private fun buildKitchenReceiptHandHeld(receiptDTO: ReceiptDTO): Bitmap? {
        val binding = LayoutHKitchenReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutKitchenReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvOrderNo.text = "Order #: ${receiptDTO.orderNo}"
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = HKitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)
        if(receiptDTO.orderSubtitle.isNullOrEmpty()){
            binding.tvOrderSubtitle.visibility = View.GONE
        }
        receipt.measure( View.MeasureSpec.makeMeasureSpec(handheldPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)
    }

    private fun buildTipsReceiptForServer(receiptDTO: ReceiptDTO) : Bitmap? {
        val binding = LayoutPTipsReceiptBinding.inflate(LayoutInflater.from(context.get()))
        val receipt = binding.layoutTipsReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvServerName.text = receiptDTO.serverName
        binding.tvServerId.text = receiptDTO.serverId
        binding.rvTipsInfoBreakdown.adapter = PTipsInfoBreakdownListAdapter(receiptDTO.tipsInfoBreakdown)
        binding.rvTipsInfoBreakdown.layoutManager = LinearLayoutManager(context.get(), RecyclerView.VERTICAL, false)

        receipt.measure( View.MeasureSpec.makeMeasureSpec(posPaperWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        receipt.layout(0, 0, receipt.measuredWidth, receipt.measuredHeight)

        return Utils.generateBitmap(receipt)
    }


}