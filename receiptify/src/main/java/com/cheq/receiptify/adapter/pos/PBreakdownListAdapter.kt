package com.cheq.receiptify.adapter.pos

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.Breakdown
import com.cheq.receiptify.databinding.LayoutPMetaInfoBinding

class PBreakdownListAdapter(private val breakdowns: List<Breakdown>) : RecyclerView.Adapter<PBreakdownListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : LayoutPMetaInfoBinding = LayoutPMetaInfoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val breakdown = breakdowns[position]
        holder.binding.tvBreakdownKeyName.text = breakdown.key
        holder.binding.tvBreakdownKeyValue.text = breakdown.value

        if(breakdown.important){
            val typeface = holder.binding.tvBreakdownKeyName.typeface
            holder.binding.tvBreakdownKeyName.setTypeface(typeface,android.graphics.Typeface.BOLD)
            holder.binding.tvBreakdownKeyValue.setTypeface(typeface,android.graphics.Typeface.BOLD)
            holder.binding.tvBreakdownKeyName.setTextSize(TypedValue.COMPLEX_UNIT_MM,4f)
            holder.binding.tvBreakdownKeyValue.setTextSize(TypedValue.COMPLEX_UNIT_MM,4f)
        }

        if(breakdown.gap){
            val params = holder.binding.tvBreakdownKeyName.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 16// Set top margin in pixels
            params.bottomMargin = 16 // Set bottom margin in pixels
            holder.binding.tvBreakdownKeyName.layoutParams = params
        }

    }

    override fun getItemCount(): Int {
        return breakdowns.size
    }

    class ViewHolder(var binding: LayoutPMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

}