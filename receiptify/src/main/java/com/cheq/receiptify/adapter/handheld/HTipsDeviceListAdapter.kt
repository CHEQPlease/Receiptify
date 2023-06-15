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
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    class ViewHolder(var binding: LayoutHTipsMetaInfoBinding) : RecyclerView.ViewHolder(binding.root)

}

