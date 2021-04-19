package com.nerdz.selfieapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.nerdz.selfieapp.R

class HelloFragment : Fragment() {
    private lateinit var selfieButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hello, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bgImageView = view.findViewById<ImageView>(R.id.hello_imageview)
        Glide.with(this).load(resources.getDrawable(R.drawable.smile)).circleCrop().into(bgImageView)

        selfieButton = view.findViewById(R.id.take_selfie_button)
        selfieButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_helloFragment_to_cameraFragment)
        }

    }
}