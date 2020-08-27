package com.axxess.assignmentnikesh.ui.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axxess.assignmentnikesh.Communicator
import com.axxess.assignmentnikesh.MyApplication
import com.axxess.assignmentnikesh.R
import com.axxess.assignmentnikesh.network.ApiInterface
import com.axxess.assignmentnikesh.network.CallHandler
import com.axxess.assignmentnikesh.network.model.BaseResponse
import com.axxess.assignmentnikesh.network.model.Image
import com.axxess.assignmentnikesh.ui.viewmodel.ImageSearchViewModel
import javax.inject.Inject


class ImageSearchFragment : Fragment(), CallHandler, View.OnClickListener, TextWatcher {

    companion object {
        fun newInstance() =
            ImageSearchFragment()

        private const val TAG = "ImageSearchFragment"
    }

    private var previousSearchTxt: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBox: AppCompatEditText
    private var activtyCommunicator: Communicator? = null
    private var adapter: ImageSearchAdapter? = null

    @Inject
    lateinit var apiInterface: ApiInterface

    private lateinit var viewModel: ImageSearchViewModel

    lateinit var imageSearchFactory: ImageSearchViewModel.Factory
    private var emptyErrorView: View? = null

    override fun onAttach(context: Context) {
        activity?.application.let {
            if (it is MyApplication) {
                it.appComponent.inject(this)
            }
        }
        if (context is Communicator) {
            activtyCommunicator = context as Communicator
        }

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        adapter = ImageSearchAdapter(this)

    }

    private fun initViewModel() {
        imageSearchFactory = ImageSearchViewModel.Factory(apiInterface)
        viewModel =
            ViewModelProvider(this, imageSearchFactory).get(ImageSearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_search_fragment, container, false)

        activtyCommunicator?.setupActionBar("Search", false)
        searchBox = view.findViewById(R.id.image_search_edit_box) as AppCompatEditText
        searchBox.addTextChangedListener(this)
        recyclerView = view.findViewById(R.id.image_search_grid_recycler) as RecyclerView
        recyclerView.visibility = View.GONE
        recyclerView.layoutManager =
            GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.adapter = adapter

        emptyErrorView = view.findViewById(R.id.search_empty_error_view) as LinearLayout
        emptyErrorView?.visibility = View.VISIBLE
        viewModel.baseResponseLiveData.observe(viewLifecycleOwner, Observer {
            it.let { baseResponse ->

                // Hide progress dialog
                activtyCommunicator?.hideProgressDialog()

                if (baseResponse.data == null || baseResponse?.data!!.isEmpty()) {
                    emptyErrorView?.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    return@Observer
                }
                emptyErrorView?.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                adapter?.setImageData(baseResponse.data)
            }
        })
        return view
    }

    override fun onSuccess(response: BaseResponse?) {
        Log.e("nikesh", "Success");
        emptyErrorView?.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        activtyCommunicator?.hideProgressDialog()
    }

    override fun onFailure(message: String?, response: BaseResponse?) {
        // Failed to search image
        activtyCommunicator?.hideProgressDialog()
        emptyErrorView?.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        previousSearchTxt = ""
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.item_search_image_view ->
                onImageClick(view)
        }
    }

    private fun onImageClick(view: View) {
        val imageData: ArrayList<Image>? =
            view.getTag(R.id.item_search_image_view) as ArrayList<Image>?
        val title = view.getTag(R.id.image_search_edit_box) as String?
        Log.d(Companion.TAG, "onImageClick: $imageData")
        imageData?.let {
            if (it.isNotEmpty()) {
                activtyCommunicator?.startFragment(ImageDetailsFragment.newInstance(it, title))
            }
        }
    }

    override fun afterTextChanged(editText: Editable?) {
        Log.d(TAG, "afterTextChanged: ")

//        viewModel.getSearchResponse(searchBox.text.toString(), this)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(editText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Log.d(TAG, "onTextChanged: ")

        if (previousSearchTxt == editText.toString()) {
            return
        }

        // Display progress dialog
        activtyCommunicator?.showProgressDialog()
        emptyErrorView?.visibility = View.GONE

        val tempTxt = editText.toString()
        val r = Runnable {

            if (searchBox.text.toString() == "") {
                emptyErrorView?.visibility = View.VISIBLE
                emptyErrorView?.bringToFront()
                return@Runnable
            }
            if (tempTxt == searchBox.text.toString()) {
                previousSearchTxt = searchBox.text.toString()
                viewModel.getSearchResponse(searchBox.text.toString(), this)
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(r, 1500)
    }
}