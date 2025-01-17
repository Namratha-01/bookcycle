package com.example.bookcycle

import FileItem
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FileAdapter(
    private val context: Context,
    private var files: List<FileItem>,
    private val itemClickListener: (FileItem) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
//        private val descriptionTextView: TextView = itemView.findViewById(R.id.bookDescriptionTextView)

        fun bind(fileItem: FileItem) {
            nameTextView.text = fileItem.title
            categoryTextView.text = fileItem.category
            priceTextView.text = "${fileItem.price} CAD"
//            descriptionTextView.text = fileItem.description

            // Load image using Glide
            Glide.with(context)
                .load(fileItem.image)
                .placeholder(R.drawable.placeholder_image) // Placeholder image
                .error(R.drawable.error) // Error image
                .into(imageView)

            itemView.setOnClickListener {
                // Pass the clicked item to the click listener
                itemClickListener(fileItem)

                // Intent to navigate to BookDetailsActivity
                val intent = Intent(context, BookDetailsActivity::class.java).apply {
                    putExtra("title", fileItem.title)
                    putExtra("category", fileItem.category)
                    putExtra("description", fileItem.description)
                    putExtra("price", fileItem.price)
                    putExtra("contact", fileItem.contact)
                    putExtra("imageUrl", fileItem.image)
                    putExtra("author", fileItem.author)
                    putExtra("email", fileItem.email)
//                    putExtra("description", fileItem.description)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files[position]
        holder.bind(fileItem)
    }

    override fun getItemCount(): Int {
        return files.size
    }

    fun updateList(newList: List<FileItem>) {
        files = newList
        notifyDataSetChanged()
    }
}
