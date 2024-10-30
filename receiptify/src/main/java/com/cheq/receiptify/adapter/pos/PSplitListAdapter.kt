package com.cheq.receiptify.adapter.handheld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.adapter.pos.PBreakdownListAdapter
import com.cheq.receiptify.data.Breakdown
import com.cheq.receiptify.databinding.LayoutPSplitMetaInfoBinding
import com.cheq.receiptify.databinding.LayoutSplitMetaInfoBinding


class PSplitListAdapter(private val splitsList: List<List<Breakdown>>) :
    RecyclerView.Adapter<PSplitListAdapter.SplitGroupViewHolder>() {

    inner class SplitGroupViewHolder(val binding: LayoutPSplitMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitGroupViewHolder {
        val binding = LayoutPSplitMetaInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SplitGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SplitGroupViewHolder, position: Int) {
        val breakdownList = splitsList[position]

        // Setup inner RecyclerView
        holder.binding.recyclerViewInner.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.recyclerViewInner.adapter = PBreakdownListAdapter(breakdownList)

        val params = holder.binding.recyclerViewInner.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = 24 // Set bottom margin in pixels
        holder.binding.recyclerViewInner.layoutParams = params
    }

    override fun getItemCount(): Int = splitsList.size
}


