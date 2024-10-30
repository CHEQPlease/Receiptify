package com.cheq.receiptify.adapter.handheld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Breakdown
import com.cheq.receiptify.databinding.LayoutSplitMetaInfoBinding


class HSplitListAdapter(private val splitsList: List<List<Breakdown>>) :
    RecyclerView.Adapter<HSplitListAdapter.SplitGroupViewHolder>() {

    inner class SplitGroupViewHolder(val binding: LayoutSplitMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitGroupViewHolder {
        val binding = LayoutSplitMetaInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SplitGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SplitGroupViewHolder, position: Int) {
        val breakdownList = splitsList[position]

        // Setup inner RecyclerView
        holder.binding.recyclerViewInner.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.recyclerViewInner.adapter = HBreakdownListAdapter(breakdownList)

        val params = holder.binding.recyclerViewInner.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 24 // Set bottom margin in pixels
        holder.binding.recyclerViewInner.layoutParams = params
    }

    override fun getItemCount(): Int = splitsList.size
}


