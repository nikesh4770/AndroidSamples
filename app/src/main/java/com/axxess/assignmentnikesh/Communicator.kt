package com.axxess.assignmentnikesh

import androidx.fragment.app.Fragment


interface Communicator {

    /**
     * Add fragment to Fragment container and navigate given fragment.
     *
     * @param fragment Name of fragment to navigate
     */
    fun startFragment(fragment: Fragment)

    /**
     * @param fragmentTag Fragment tag that to check.
     * @return `true` Return true if the fragment is already added.
     * `false` Returns false if the fragment is not added earlier.
     */
    fun isFragmentAlreadyAdded(fragmentTag: String): Boolean

    fun setupActionBar(title: String, backEnable: Boolean)

    fun showProgressDialog()

    fun hideProgressDialog()
}