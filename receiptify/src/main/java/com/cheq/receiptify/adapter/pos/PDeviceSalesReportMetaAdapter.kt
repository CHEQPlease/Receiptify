package com.cheq.receiptify.adapter.pos

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.MetaData
import com.cheq.receiptify.databinding.LayoutHDeviceSalesMetaInfoBinding
import com.cheq.receiptify.databinding.LayoutPDeviceSalesMetaInfoBinding


class PDeviceSalesReportMetaAdapter(private val metas: List<MetaData>) : RecyclerView.Adapter<PDeviceSalesReportMetaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutPDeviceSalesMetaInfoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val metaData = metas[position]
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
        return metas.size
    }

    class ViewHolder(var binding: LayoutPDeviceSalesMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

