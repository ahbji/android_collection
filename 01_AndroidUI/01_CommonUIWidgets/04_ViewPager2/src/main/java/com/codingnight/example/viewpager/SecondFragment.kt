package com.codingnight.example.viewpager

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingnight.example.viewpager.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {
    private lateinit var binding: SecondFragmentBinding
    private var mViewModel: SecondViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondFragmentBinding.inflate(layoutInflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(SecondViewModel::class.java)
        val imageView = binding.imageView
        imageView.scaleX = mViewModel!!.scaleFactor
        imageView.scaleY = mViewModel!!.scaleFactor
        val objectAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 0f)
        val objectAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 0f)
        objectAnimatorX.duration = 500
        objectAnimatorY.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimatorX.isRunning) {
                objectAnimatorX.setFloatValues(imageView.scaleX + 0.1f)
                objectAnimatorY.setFloatValues(imageView.scaleY + 0.1f)
                mViewModel!!.scaleFactor += 0.1f
                objectAnimatorX.start()
                objectAnimatorY.start()
            }
        }
    }

    companion object {
        fun newInstance(): SecondFragment {
            return SecondFragment()
        }
    }
}