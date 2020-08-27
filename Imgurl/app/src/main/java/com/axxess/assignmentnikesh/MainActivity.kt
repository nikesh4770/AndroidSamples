package com.axxess.assignmentnikesh

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.axxess.assignmentnikesh.ui.view.ImageSearchFragment


class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout: RelativeLayout = findViewById(R.id.activity_view)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
        hideProgressDialog()

        startFragment(ImageSearchFragment.newInstance())
    }

    override fun setupActionBar(title: String, backEnable: Boolean) {

        supportActionBar?.title = title
        supportActionBar?.setHomeButtonEnabled(backEnable)
        supportActionBar?.setDisplayHomeAsUpEnabled(backEnable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()

        val currentBackStackIndex = fragmentManager.backStackEntryCount - 1
        if (fragmentManager.backStackEntryCount == 0 || currentBackStackIndex <= 0) {
            finish()
            return
        }

        val currentTag =
            fragmentManager.getBackStackEntryAt(currentBackStackIndex).name
        Log.d(TAG, " Current Fragment Tag - $currentTag")
        if (currentTag.equals(ImageSearchFragment.javaClass.simpleName, true)) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }

    /**
     * Display progress dialog with custom message
     */
    override fun showProgressDialog() {
        Log.v(TAG, "In showProgressDialog()")

        progressBar.visibility = View.VISIBLE
    }

    /**
     * Hides the progress dialog.
     */
    override fun hideProgressDialog() {
        Log.v(TAG, "In hideProgressDialog()")
        progressBar.visibility = View.INVISIBLE
    }


    override fun startFragment(fragment: Fragment) {
        if (isFinishing) {
            return
        }
        if (isFragmentAlreadyAdded(fragment.javaClass.simpleName)) {
            return
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragmentTag: String = fragment.javaClass.simpleName
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragmentTag)
        fragmentTransaction.addToBackStack(fragmentTag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * @param fragmentTag Fragment tag that to check.
     * @return `true` Return true if the fragment is already added.
     * `false` Returns false if the fragment is not added earlier.
     */
    override fun isFragmentAlreadyAdded(fragmentTag: String): Boolean {
        val fragmentManager: FragmentManager = supportFragmentManager
        return fragmentManager.findFragmentByTag(fragmentTag) != null
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}