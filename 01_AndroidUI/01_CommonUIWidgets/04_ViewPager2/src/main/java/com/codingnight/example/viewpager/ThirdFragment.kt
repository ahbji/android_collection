package com.codingnight.example.viewpager

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingnight.example.viewpager.databinding.ThirdFragmentBinding
import java.util.*

class ThirdFragment : Fragment() {


    private lateinit var binding: ThirdFragmentBinding
    private var mViewModel: ThirdViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ThirdFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(ThirdViewModel::class.java)
        val imageView = binding.imageView
        imageView.x = imageView.x + mViewModel!!.dX
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "x", 0f, 0f)
        objectAnimator.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimator.isRunning) {
                val dx: Float = if (Random().nextBoolean()) 100F else (-100).toFloat()
                objectAnimator.setFloatValues(imageView.x, imageView.x + dx)
                mViewModel!!.dX += dx
                objectAnimator.start()
            }
        }
    }

    companion object {
        fun newInstance(): ThirdFragment {
            return ThirdFragment()
        }
    }
}