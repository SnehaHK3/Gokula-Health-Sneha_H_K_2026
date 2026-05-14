package com.gokulahealth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gokulahealth.R
import com.gokulahealth.data.MilkEntry

class MilkAdapter : ListAdapter<MilkEntry, MilkAdapter.MilkViewHolder>(DIFF_CALLBACK) {

    inner class MilkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDate:    TextView = view.findViewById(R.id.tvMilkDate)
        val tvMorning: TextView = view.findViewById(R.id.tvMorning)
        val tvEvening: TextView = view.findViewById(R.id.tvEvening)
        val tvTotal:   TextView = view.findViewById(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_milk, parent, false)
        return MilkViewHolder(view)
    }

    override fun onBindViewHolder(holder: MilkViewHolder, position: Int) {
        val entry = getItem(position)
        holder.tvDate.text    = entry.date
        holder.tvMorning.text = "🌅 Morning: ${entry.morningLiters}L"
        holder.tvEvening.text = "🌆 Evening: ${entry.eveningLiters}L"
        holder.tvTotal.text   = "Total: ${entry.totalLiters}L"
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MilkEntry>() {
            override fun areItemsTheSame(a: MilkEntry, b: MilkEntry) = a.id == b.id
            override fun areContentsTheSame(a: MilkEntry, b: MilkEntry) = a == b
        }
    }
}