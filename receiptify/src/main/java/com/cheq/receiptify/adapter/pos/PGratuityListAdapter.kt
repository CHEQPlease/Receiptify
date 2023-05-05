package com.cheq.receiptify.adapter.pos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.GratuityItem
import com.cheq.receiptify.databinding.LayoutPGratuitySubsectionBinding


class PGratuityListAdapter(private val gratuityItems : List<GratuityItem>) : RecyclerView.Adapter<PGratuityListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPGratuitySubsectionBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val gratuityItem = gratuityItems[position]
        holder.binding.tvGratuityAmount.text = gratuityItem.tip
        holder.binding.tvGratuityTotal.text = gratuityItem.total
    }

    override fun getItemCount(): Int {
        return gratuityItems.size
    }

    class ViewHolder(var binding: LayoutPGratuitySubsectionBinding) : RecyclerView.ViewHolder(binding.root)

}

