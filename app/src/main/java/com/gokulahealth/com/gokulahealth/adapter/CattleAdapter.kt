package com.gokulahealth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gokulahealth.R
import com.gokulahealth.data.Cattle

class CattleAdapter(private val onClick: (Cattle) -> Unit) :
    ListAdapter<Cattle, CattleAdapter.CattleViewHolder>(DIFF_CALLBACK) {

    inner class CattleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName:   TextView = view.findViewById(R.id.tvCowName)
        val tvEarTag: TextView = view.findViewById(R.id.tvEarTag)
        val tvBreed:  TextView = view.findViewById(R.id.tvBreed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cattle, parent, false)
        return CattleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CattleViewHolder, position: Int) {
        val cattle = getItem(position)
        holder.tvName.text   = cattle.name
        holder.tvEarTag.text = "🏷 ${cattle.earTagId}"
        holder.tvBreed.text  = cattle.breed
        holder.itemView.setOnClickListener { onClick(cattle) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cattle>() {
            override fun areItemsTheSame(a: Cattle, b: Cattle) = a.id == b.id
            override fun areContentsTheSame(a: Cattle, b: Cattle) = a == b
        }
    }
}