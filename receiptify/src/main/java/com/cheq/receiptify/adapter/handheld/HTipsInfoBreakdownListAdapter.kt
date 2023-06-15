package com.cheq.receiptify.adapter.handheld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.TipsInfoBreakdown
import com.cheq.receiptify.databinding.LayoutHTipsInfoBinding
import com.cheq.receiptify.databinding.LayoutPTipsInfoBinding


class HTipsInfoBreakdownListAdapter(private val tipsInfoBreakdown: List<TipsInfoBreakdown>) : RecyclerView.Adapter<HTipsInfoBreakdownListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHTipsInfoBinding.inflate(
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

    class ViewHolder(var binding: LayoutHTipsInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

