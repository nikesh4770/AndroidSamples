package com.axxess.assignmentnikesh.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.model.Comment
import com.axxess.assignmentnikesh.network.model.Image

class CommentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<Comment?> = ArrayList(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment_image_recycler, parent, false)

        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setCommentData(data: List<Comment?>?) {
        data?.let {
            this.data = ArrayList(data)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is CommentViewHolder) {
            return
        }
        data[position]?.let {
            Log.d(TAG, "onBindViewHolder: ${it.description}")

            holder.comment.text = it.description
        }
    }

    internal class CommentViewHolder(
        listItemView: View
    ) : RecyclerView.ViewHolder(listItemView) {

        var comment: AppCompatTextView =
            listItemView.findViewById(R.id.item_comment_text)
    }

    companion object {
        private const val TAG = "CommentAdapter"
    }
}
