package com.cheq.receiptify.adapter.handheld
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cheq.receiptify.data.TipsPerRevenueCenter
import com.cheq.receiptify.databinding.LayoutHTipRevenueCenterBinding


class HTipsPerRevenueCenterListAdapter(private val tipsPerRevenueCenter: List<TipsPerRevenueCenter>) :
    RecyclerView.Adapter<HTipsPerRevenueCenterListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHTipRevenueCenterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val tipsPerRevenueCenter = tipsPerRevenueCenter[position]
        holder.binding.tvCenterName.text = tipsPerRevenueCenter.name
        holder.binding.tvTotalTipsValue.text = tipsPerRevenueCenter.tip
        holder.binding.rvDeviceList.adapter =
            HTipsDeviceListAdapter(tipsPerRevenueCenter.deviceList)
        holder.binding.rvDeviceList.layoutManager =
            LinearLayoutManager(holder.binding.rvDeviceList.context, RecyclerView.VERTICAL, false)

        if(position == itemCount - 1){
            val layoutParams = holder.binding.rvDeviceList.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 0
            holder.binding.rvDeviceList.layoutParams = layoutParams
        } else {
            // Convert 3 mm to pixels
            val displayMetrics = holder.binding.rvDeviceList.context.resources.displayMetrics
            val marginInPixels = (3 * displayMetrics.xdpi / 25.4).toInt()

            // Set the bottom margin to 3 mm in pixels
            val layoutParams = holder.binding.rvDeviceList.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = marginInPixels
            holder.binding.rvDeviceList.layoutParams = layoutParams
        }

    }

    override fun getItemCount(): Int {
        return tipsPerRevenueCenter.size
    }

    class ViewHolder(var binding: LayoutHTipRevenueCenterBinding) :
        RecyclerView.ViewHolder(binding.root)

}

