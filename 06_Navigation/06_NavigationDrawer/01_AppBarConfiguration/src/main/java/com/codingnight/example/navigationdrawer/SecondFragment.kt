package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SecondFragment : Fragment() {
    private var mViewModel: SecondViewModel? = null
    private var imageView: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.second_fragment, container, false)
        imageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(
            SecondViewModel::class.java
        )
        imageView!!.scaleX = mViewModel!!.scaleFactor
        imageView!!.scaleY = mViewModel!!.scaleFactor
        // TODO: Use the ViewModel
        val objectAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 0f)
        val objectAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 0f)
        objectAnimatorX.duration = 500
        objectAnimatorY.duration = 500
        imageView!!.setOnClickListener {
            if (!objectAnimatorX.isRunning) {
                objectAnimatorX.setFloatValues(imageView!!.scaleX + 0.1f)
                objectAnimatorY.setFloatValues(imageView!!.scaleY + 0.1f)
                mViewModel!!.scaleFactor += 0.1f
                objectAnimatorX.start()
                objectAnimatorY.start()
            }
        }
    }
}