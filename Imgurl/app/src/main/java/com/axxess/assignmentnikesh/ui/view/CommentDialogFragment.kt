package com.axxess.assignmentnikesh.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axxess.assignmentnikesh.MyApplication
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.model.Comment
import com.axxess.assignmentnikesh.network.model.Image
import com.axxess.assignmentnikesh.room.CommentRepository
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [CommentDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var commentBox: AppCompatEditText
    private var commentAdapter: CommentAdapter? = null
    private lateinit var imageDetails: Image

    @Inject
    lateinit var commentRepository: CommentRepository

    override fun onAttach(context: Context) {
        activity?.application.let {
            if (it is MyApplication) {
                it.appComponent.inject(this)
            }
        }
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageDetails = it.getSerializable("IMAGE_DATA") as Image
        }
        commentAdapter = CommentAdapter()
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetFix)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_comment_dialog, container, false)

        commentBox = view.findViewById(R.id.comment_dialog_edit_text) as AppCompatEditText

        val commentBtn =
            view.findViewById(R.id.comment_dialog_add_text) as AppCompatTextView
        commentBtn.setOnClickListener(this)

        val commentRecycler = view.findViewById(R.id.comment_dialog_recycler) as RecyclerView
        commentRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        commentRecycler.adapter = commentAdapter

        imageDetails.id?.let {
            commentRepository.findAll(it)
                ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {dbList->
                    dbList?.let { commentList ->
                        Log.d(TAG, "onCreateView: $commentList")
                        commentAdapter?.setCommentData(commentList.reversed())
                    }
                })
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentDialogFragment.
         */
        @JvmStatic
        fun newInstance(param1: Image?) =
            CommentDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("IMAGE_DATA", param1)
                }
            }

        internal const val TAG = "CommentDialogFragment"
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.comment_dialog_add_text ->
                onAddClick(view)
        }
    }

    private fun onAddClick(view: View) {
        val comment = imageDetails.id?.let {
            Comment(
                0, it, commentBox.text.toString()
            )
        }

        Thread(Runnable {
            try {
                commentRepository.insert(comment)
                commentBox.setText("", TextView.BufferType.EDITABLE);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()

    }
}