package com.nikesh.kitchenreciepies.view.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.nikesh.kitchenreciepies.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val handler = Handler(Looper.getMainLooper())
        val navController = NavHostFragment.findNavController(this)
        handler.postDelayed(Runnable {
            navController.navigate(R.id.action_splashFragment_to_loginFragment)
        }, SPLASH_DURATION)
    }

    companion object {
        private const val SPLASH_DURATION: Long = 1500L
        private const val TAG = "SplashFragment"
    }
}