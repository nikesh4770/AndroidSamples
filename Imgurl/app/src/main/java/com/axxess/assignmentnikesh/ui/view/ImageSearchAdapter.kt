package com.axxess.assignmentnikesh.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.model.Data
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ImageSearchAdapter(private var onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<ViewHolder>() {

    private var data: ArrayList<Data>? = ArrayList(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_search_recycler, parent, false)

        return ImageItemViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (holder !is ImageItemViewHolder) {
            return
        }

        val listItem: Data? = data?.get(position)

        holder.image.setOnClickListener(onClickListener)
        listItem?.images?.let {imageList ->
            if (imageList.isNotEmpty()) {
                // Provide data for details fragment by setting tag data
                holder.image.setTag(R.id.item_search_image_view, imageList)
                holder.image.setTag(R.id.image_search_edit_box, listItem.title)

                // Showing only first image in searched keyword
                imageList[0].let { item ->
                    Picasso.get()
                        .load(item.link)
                        .placeholder(R.drawable.img_default_picture)
                        .noFade()
                        .resize(400, 400)
                        .centerCrop()
                        .onlyScaleDown()
                        .into(holder.image, object : Callback {
                            override fun onSuccess() {
                                if (imageList.size > 1) {
                                    holder.moreIcon.visibility = View.VISIBLE
                                } else {
                                    holder.moreIcon.visibility = View.INVISIBLE
                                }
                            }

                            override fun onError(e: Exception?) {
                            }

                        })
                }
            }
        }
    }

    fun setImageData(data: ArrayList<Data>?) {
        this.data = data ?: ArrayList()
        notifyDataSetChanged()
    }

    internal class ImageItemViewHolder(
        listItemView: View,
        onClickListener: View.OnClickListener
    ) :
        ViewHolder(listItemView) {

        var image: AppCompatImageView = listItemView.findViewById(R.id.item_search_image_view)
        var moreIcon: AppCompatImageView =
            listItemView.findViewById(R.id.item_search_image_more_icon)
    }
}


