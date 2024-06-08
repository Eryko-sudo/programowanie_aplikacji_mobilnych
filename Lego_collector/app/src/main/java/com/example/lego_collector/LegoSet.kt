package com.example.lego_collector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.Serializable

@Serializable
data class LegoSet(
    val set_num: String,
    val name: String,
    val year: Int,
    val theme_id: Int,
    val num_parts: Int,
    val set_img_url: String,
    val set_url: String,
    val last_modified_dt: String
)

class LegoSetAdapter(private val legoSets: List<LegoSet>) : RecyclerView.Adapter<LegoSetAdapter.LegoSetViewHolder>() {

    class LegoSetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegoSetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lego_set_item, parent, false)
        return LegoSetViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegoSetViewHolder, position: Int) {
        val legoSet = legoSets[position]
        holder.textView.text = legoSet.name
        // Use a library like Glide or Picasso to load the image from the URL
        // Glide.with(holder.imageView.context).load(legoSet.set_img_url).into(holder.imageView)
    }

    override fun getItemCount() = legoSets.size
}