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


//class HSplitListAdapter(private val splitsList: List<List<Breakdown>>) : RecyclerView.Adapter<HSplitListAdapter.SplitViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SplitViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_split_meta_info, parent, false)
//        return SplitViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: SplitViewHolder, position: Int) {
//        val splitGroup = splitsList[position]
//
//        // Set up inner RecyclerView
//        holder.recyclerViewInner.adapter = HBreakdownListAdapter(splitGroup)
//        holder.recyclerViewInner.layoutManager = LinearLayoutManager(holder.itemView.context)
//    }
//
//    override fun getItemCount(): Int = splitsList.size
//
//    inner class SplitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val recyclerViewInner: RecyclerView = itemView.findViewById(R.id.recyclerViewInner)
//    }
//}
