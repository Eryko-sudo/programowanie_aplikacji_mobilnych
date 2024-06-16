package com.example.lego_collector

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.serialization.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Serializable
data class LegoSet(
    @PrimaryKey val set_num: String,
    val name: String,
    val year: Int,
    val theme_id: Int,
    val num_parts: Int,
    val set_img_url: String?,
    val set_url: String,
    val last_modified_dt: String
) : java.io.Serializable

class LegoSetAdapter(private var legoSets: List<LegoSet>) : RecyclerView.Adapter<LegoSetAdapter.LegoSetViewHolder>() {

    class LegoSetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegoSetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lego_tiles, parent, false)
        return LegoSetViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegoSetViewHolder, position: Int) {
        val legoSet = legoSets[position]
        holder.textView.text = legoSet.name

        Glide.with(holder.imageView.context)
            .load(legoSet.set_img_url)
            .fitCenter()
            .into(holder.imageView)

        // Set click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, LegoSetDetailActivity::class.java)
            intent.putExtra("legoSet", legoSet)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = legoSets.size

    fun updateLegoSets(newLegoSets: List<LegoSet>) {
        legoSets = newLegoSets
        notifyDataSetChanged()
    }
}