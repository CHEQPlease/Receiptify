package com.cheq.receiptify.adapter.handheld
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.databinding.LayoutHTipsMetaInfoBinding

class HTipsDeviceListAdapter(private val deviceList : List<String>) : RecyclerView.Adapter<HTipsDeviceListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHTipsMetaInfoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val deviceList = deviceList[position]
        holder.binding.tvTipsMetaInfo.text = deviceList

        if (position == itemCount - 1) {
            val layoutParams = holder.binding.tvTipsMetaInfo.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 0
            holder.binding.tvTipsMetaInfo.layoutParams = layoutParams
        } else {
            // Convert 3 mm to pixels
            val displayMetrics = holder.binding.tvTipsMetaInfo.context.resources.displayMetrics
            val marginInPixels = (3 * displayMetrics.xdpi / 25.4).toInt()

            // Set the bottom margin to 3 mm in pixels
            val layoutParams = holder.binding.tvTipsMetaInfo.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = marginInPixels
            holder.binding.tvTipsMetaInfo.layoutParams = layoutParams
        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    class ViewHolder(var binding: LayoutHTipsMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

