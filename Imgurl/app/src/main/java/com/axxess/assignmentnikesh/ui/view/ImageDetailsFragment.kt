package com.axxess.assignmentnikesh.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axxess.assignmentnikesh.Communicator
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.model.Image

/**
 * A simple [Fragment] subclass.
 * Use the [ImageDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageDetailsFragment : Fragment(), View.OnClickListener {

    private var detailsAdapter: DetailsAdapter? = null
    private var data: ArrayList<Image>? = ArrayList(0)
    private var activityCommunicator: Communicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator) {
            activityCommunicator = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = arguments?.getSerializable("IMAGE_DETAILS") as ArrayList<Image>?
        val title = arguments?.getString("IMAGE_TITLE")
        detailsAdapter = DetailsAdapter(this, context)

        activityCommunicator?.setupActionBar(title ?: "Image Detail", true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_details, container, false)
        val detailsRecycler = view.findViewById(R.id.image_details_recycler) as RecyclerView
        detailsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        detailsRecycler.adapter = detailsAdapter
        detailsAdapter?.setImageData(data = data)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ImageDetailsFragment.
         */
        @JvmStatic
        fun newInstance(data: ArrayList<Image>?, title: String?) =
            ImageDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("IMAGE_DETAILS", data)
                    putString("IMAGE_TITLE", title)
                }
            }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item_image_details_comment_icon ->
                onCommentClick(view)
        }
    }

    private fun onCommentClick(view: View?) {
        view?.getTag(R.id.item_image_details_comment_icon).let {
            val imageData = view?.getTag(R.id.item_image_details_comment_icon) as Image
            showBottomSheet(imageData)
        }
    }

    private fun showBottomSheet(imageData: Image?) {
        val commentDialogFragment: CommentDialogFragment =
            CommentDialogFragment.newInstance(imageData)
        commentDialogFragment.isCancelable = true
        activity?.supportFragmentManager?.let {
            commentDialogFragment.show(
                it,
                CommentDialogFragment.TAG
            )
        }
    }
}