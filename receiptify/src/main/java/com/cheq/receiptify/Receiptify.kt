package com.cheq.receiptify

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.adapter.BreakdownListAdapter
import com.cheq.receiptify.adapter.DishListAdapterCustomer
import com.cheq.receiptify.adapter.KitchenDishListAdapter
import com.cheq.receiptify.utils.SingletonHolder
import com.cheq.receiptify.data.ReceiptDTO
import com.cheq.receiptify.data.ReceiptType
import com.cheq.receiptify.databinding.*
import com.cheq.receiptify.utils.Utils


class Receiptify private constructor(context: Context) {

    private val context = context.applicationContext
    companion object : SingletonHolder<Receiptify, Context>(::Receiptify)

    fun buildReceipt(receiptDTO: ReceiptDTO): Bitmap? {

        when(receiptDTO.receiptType?.uppercase()){
            ReceiptType.CUSTOMER_H.name ->{
                return buildCustomerReceiptHandheld(receiptDTO)
            }
            ReceiptType.CUSTOMER_P.name -> {
                return buildCustomerReceiptPOS(receiptDTO)

            }
            ReceiptType.KIOSK_H.name ->{
                return buildKioskReceiptHandheld(receiptDTO)

            }
            ReceiptType.KIOSK_P.name ->{
                return buildKioskReceiptPOS(receiptDTO)
            }
            ReceiptType.MERCHANT_H.name -> {
                return buildMerchantReceiptHandheld(receiptDTO)

            }
            ReceiptType.MERCHANT_P.name ->{
                return buildMerchantReceiptPOS(receiptDTO)
            }
            ReceiptType.MERCHANT_H.name -> {
                return buildMerchantReceiptHandheld(receiptDTO)

            }
            ReceiptType.KITCHEN_P.name ->{
                return buildKitchenReceiptPOS(receiptDTO)

            }
            ReceiptType.KITCHEN_H.name ->{
                return buildKitchenReceiptHandHeld(receiptDTO)
            }
        }

        return  null

    }

    private fun buildCustomerReceiptPOS(receiptDTO: ReceiptDTO): Bitmap? {
        val binding = LayoutPCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceipt = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = DishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        customerReceipt.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt)
    }

    private fun buildCustomerReceiptHandheld(receiptDTO: ReceiptDTO) : Bitmap {

        val binding = LayoutHCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceipt = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future*/

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = DishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        customerReceipt.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceipt.layout(0, 0, customerReceipt.measuredWidth, customerReceipt.measuredHeight)

        return Utils.generateBitmap(customerReceipt)

    }

    private fun buildKioskReceiptHandheld(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutHCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = DishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)


        return Utils.generateBitmap(customerReceiptKiosk)
    }


    private fun buildKioskReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutPCustomerKioskReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutCustomerReceiptKiosk

        /* TODO : Move to string resource to support localization in future */

        binding.tvBrandName.text = receiptDTO.brandName
        binding.tvOrderNo.text = "ORDER #: ${receiptDTO.orderNo}"
        binding.tvTotalItems.text = "TOTAL ITEMS - ${receiptDTO.totalItems}" /* TODO : Move to plural type string resource*/
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.rvDishes.adapter = DishListAdapterCustomer(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.tvOrderType.text = receiptDTO.orderType
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)

        return Utils.generateBitmap(customerReceiptKiosk)
    }


    private fun buildMerchantReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap {
        val binding = LayoutPMerchantReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutMerchantReceiptBinding

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
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)

        return Utils.generateBitmap(customerReceiptKiosk)

    }

    private fun buildMerchantReceiptHandheld(receiptDTO: ReceiptDTO): Bitmap {
        val binding = LayoutHMerchantReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutMerchantReceiptBinding

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
        binding.rvBreakdown.adapter = BreakdownListAdapter(receiptDTO.breakdown)
        binding.rvBreakdown.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)

        return Utils.generateBitmap(customerReceiptKiosk)
    }


    private fun buildKitchenReceiptPOS(receiptDTO: ReceiptDTO) : Bitmap? {
        val binding = LayoutPKitchenReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutKitchenReceipt


        /* TODO : Move to string resource to support localization in future */

        binding.tvOrderNo.text = "Order #: ${receiptDTO.orderNo}"
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = KitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        if(receiptDTO.orderSubtitle.isNullOrEmpty()){
            binding.tvOrderSubtitle.visibility = View.GONE
        }
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)

        return Utils.generateBitmap(customerReceiptKiosk)

    }

    private fun buildKitchenReceiptHandHeld(receiptDTO: ReceiptDTO): Bitmap? {
        val binding = LayoutHKitchenReceiptBinding.inflate(LayoutInflater.from(context))
        val customerReceiptKiosk = binding.layoutKitchenReceipt

        /* TODO : Move to string resource to support localization in future */

        binding.tvOrderNo.text = "Order #: ${receiptDTO.orderNo}"
        binding.tvPlacedAt.text = receiptDTO.timeOfOrder
        binding.tvOrderSubtitle.text = receiptDTO.orderSubtitle
        binding.rvDishes.adapter = KitchenDishListAdapter(receiptDTO.items)
        binding.rvDishes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        if(receiptDTO.orderSubtitle.isNullOrEmpty()){
            binding.tvOrderSubtitle.visibility = View.GONE
        }
        customerReceiptKiosk.measure( View.MeasureSpec.makeMeasureSpec(700, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        customerReceiptKiosk.layout(0, 0, customerReceiptKiosk.measuredWidth, customerReceiptKiosk.measuredHeight)

        return Utils.generateBitmap(customerReceiptKiosk)
    }


}