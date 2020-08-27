package com.axxess.assignmentnikesh.ui.view

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.model.Image
import com.squareup.picasso.Picasso


class DetailsAdapter(
    private var onClickListener: View.OnClickListener,
    private var context: Context?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: ArrayList<Image>? = ArrayList(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_details_recycler, parent, false)

        return ImageDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun setImageData(data: ArrayList<Image>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder !is ImageDetailsViewHolder) {
            return
        }

        holder.commentButton.setOnClickListener(onClickListener)

        data?.get(position)?.let { image ->
            image.let {
                holder.commentButton.setTag(R.id.item_image_details_comment_icon, it)
                holder.imageName.text = it.title ?: "image$position"
                val displayMetrics = context?.resources?.displayMetrics
                var width = 800
                var height = 1000
                displayMetrics?.let {
                    width = displayMetrics.widthPixels
                    height = displayMetrics.heightPixels
                }

                Picasso.get()
                    .load(image.link)
                    .fit()
                    .centerCrop()
                    .into(holder.image)
            }
        }
    }

    internal class ImageDetailsViewHolder(
        listItemView: View
    ) :
        RecyclerView.ViewHolder(listItemView) {

        var imageName: AppCompatTextView = listItemView.findViewById(R.id.item_image_details_title)
        var image: AppCompatImageView = listItemView.findViewById(R.id.item_image_details_picture)

        var commentButton: AppCompatImageView =
            listItemView.findViewById(R.id.item_image_details_comment_icon)
    }
}
