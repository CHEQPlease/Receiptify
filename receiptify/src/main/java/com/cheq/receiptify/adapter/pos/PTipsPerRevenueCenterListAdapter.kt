package com.cheq.receiptify.adapter.pos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.TipsInfoBreakdown
import com.cheq.receiptify.data.TipsPerRevenueCenter
import com.cheq.receiptify.databinding.LayoutPTipRevenueCenterBinding
import com.cheq.receiptify.databinding.LayoutPTipsInfoBinding


class PTipsPerRevenueCenterListAdapter(private val tipsPerRevenueCenter : List<TipsPerRevenueCenter>) : RecyclerView.Adapter<PTipsPerRevenueCenterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPTipRevenueCenterBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tipsPerRevenueCenter = tipsPerRevenueCenter[position]
        holder.binding.tvCenterName.text = tipsPerRevenueCenter.name
        holder.binding.tvTip.text = tipsPerRevenueCenter.tip
    }

    override fun getItemCount(): Int {
        return tipsPerRevenueCenter.size
    }

    class ViewHolder(var binding: LayoutPTipRevenueCenterBinding) : RecyclerView.ViewHolder(binding.root)

}

