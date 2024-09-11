package com.cheq.receiptify.adapter.handheld

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.PaymentBreakdown
import com.cheq.receiptify.databinding.LayoutHDeviceSalesMetaInfoBinding
import com.cheq.receiptify.databinding.LayoutHTipsInfoBinding


class HDeviceSalesReportBreakdownAdapter(private val paymentBreakdowns: List<PaymentBreakdown>) : RecyclerView.Adapter<HDeviceSalesReportBreakdownAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHDeviceSalesMetaInfoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val metaData = paymentBreakdowns[position]
        holder.binding.tvDeviceSalesMetaKey.text = metaData.key
        holder.binding.tvDeviceSalesMetaValue.text = metaData.value

        if (metaData.isBold) {
            holder.binding.tvDeviceSalesMetaKey.setTypeface(null, Typeface.BOLD)
            holder.binding.tvDeviceSalesMetaValue.setTypeface(null, Typeface.BOLD)
        } else {
            holder.binding.tvDeviceSalesMetaKey.setTypeface(null, Typeface.NORMAL)
            holder.binding.tvDeviceSalesMetaValue.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun getItemCount(): Int {
        return paymentBreakdowns.size
    }

    class ViewHolder(var binding: LayoutHDeviceSalesMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

