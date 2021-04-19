package com.nerdz.selfieapp.screens

import android.R.attr.bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.nerdz.selfieapp.R


class PreviewFragment : Fragment() {

    val args: PreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = view.findViewById<FrameLayout>(R.id.container_layout)
        val selfiePreviewView = view.findViewById<ImageView>(R.id.image_preview)
        val bmp = args.mediaBitmap
        val palette = Palette.from(bmp).generate()
        val vibrantDark= palette.getDarkVibrantColor(resources.getColor(R.color.black_overlay))
        container.setBackgroundColor(vibrantDark)

        Glide.with(this).load(bmp).circleCrop().into(selfiePreviewView)
    }

}