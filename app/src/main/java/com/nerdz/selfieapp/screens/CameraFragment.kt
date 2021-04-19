package com.nerdz.selfieapp.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.nerdz.selfieapp.R
import com.nerdz.selfielib.SelfieView
import com.nerdz.selfielib.screens.camera_screen.SelfieViewModel

class CameraFragment : Fragment() {
    private lateinit var selfieView: SelfieView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selfieView = view.findViewById(R.id.selfie_view)
        selfieView.start { result ->
            try{
                val action = CameraFragmentDirections.actionCameraFragmentToPreviewFragment(result.mediaBitmap!!)
                view.findNavController().navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}