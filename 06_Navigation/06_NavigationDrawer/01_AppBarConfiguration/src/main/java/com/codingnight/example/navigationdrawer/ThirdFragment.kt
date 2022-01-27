package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

class ThirdFragment : Fragment() {
    private var mViewModel: ThirdViewModel? = null
    private var imageView: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.third_fragment, container, false)
        imageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(
            ThirdViewModel::class.java
        )
        imageView!!.x = imageView!!.x + mViewModel!!.dX
        // TODO: Use the ViewModel
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "x", 0f, 0f)
        objectAnimator.duration = 500
        imageView!!.setOnClickListener {
            if (!objectAnimator.isRunning) {
                val dx: Float = if (Random().nextBoolean()) 100F else (-100).toFloat()
                objectAnimator.setFloatValues(imageView!!.x, imageView!!.x + dx)
                mViewModel!!.dX += dx
                objectAnimator.start()
            }
        }
    }
}