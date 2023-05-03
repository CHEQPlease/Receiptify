package com.cheq.receiptify.adapter.pos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.TipsInfoBreakdown
import com.cheq.receiptify.databinding.LayoutPTipsInfoBinding


class PTipsInfoBreakdownListAdapter(private val tipsInfoBreakdown: List<TipsInfoBreakdown>) : RecyclerView.Adapter<PTipsInfoBreakdownListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPTipsInfoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tipsInfoBreakdown = tipsInfoBreakdown[position]
        holder.binding.tvTipsBreakdownKeyName.text = tipsInfoBreakdown.key
        holder.binding.tvTipsBreakdownKeyValue.text = tipsInfoBreakdown.value
    }

    override fun getItemCount(): Int {
        return tipsInfoBreakdown.size
    }

    class ViewHolder(var binding: LayoutPTipsInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

