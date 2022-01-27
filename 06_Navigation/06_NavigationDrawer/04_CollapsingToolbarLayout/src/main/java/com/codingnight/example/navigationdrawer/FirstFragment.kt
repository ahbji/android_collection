package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codingnight.example.navigationdrawer.databinding.ActivityMainBinding
import com.codingnight.example.navigationdrawer.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {
    private var mViewModel: FirstViewModel? = null
    private var imageView: ImageView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FirstFragmentBinding.inflate(inflater)
        imageView = binding.imageView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity()).get(
            FirstViewModel::class.java
        )
        imageView!!.rotation = mViewModel!!.rotationPosition
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 0f)
        objectAnimator.duration = 500
        imageView!!.setOnClickListener {
            if (!objectAnimator.isRunning) {
                objectAnimator.setFloatValues(imageView!!.rotation, imageView!!.rotation + 100)
                mViewModel!!.rotationPosition += 100f
                objectAnimator.start()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).toolbarLayout?.title = getString(R.string.rotate)
        (requireActivity() as MainActivity).toolbarIconImageView?.setImageResource(R.drawable.ic_looks_one)
    }

    companion object {
        private const val TAG = "hello"
    }
}