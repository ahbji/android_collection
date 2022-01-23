package com.codingnight.example.viewpager

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingnight.example.viewpager.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {
    private lateinit var binding: FirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FirstFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProvider(requireActivity()).get(FirstViewModel::class.java)
        val imageView = binding.imageView
        imageView.rotation = mViewModel.rotationPosition
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 0f)
        objectAnimator.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimator.isRunning) {
                objectAnimator.setFloatValues(
                    imageView.rotation,
                    imageView.rotation + 100
                )
                mViewModel.rotationPosition += 100f
                objectAnimator.start()
            }
        }
    }

    companion object {
        fun newInstance(): FirstFragment {
            return FirstFragment()
        }
    }
}