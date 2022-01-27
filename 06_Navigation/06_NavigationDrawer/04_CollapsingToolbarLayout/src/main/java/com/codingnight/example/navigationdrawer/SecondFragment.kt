package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingnight.example.navigationdrawer.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {
    private lateinit var mViewModel: SecondViewModel
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SecondFragmentBinding.inflate(inflater)
        imageView = binding.imageView
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity()).get(
            SecondViewModel::class.java
        )
        imageView.scaleX = mViewModel.scaleFactor
        imageView.scaleY = mViewModel.scaleFactor
        // TODO: Use the ViewModel
        val objectAnimatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 0f)
        val objectAnimatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 0f)
        objectAnimatorX.duration = 500
        objectAnimatorY.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimatorX.isRunning) {
                objectAnimatorX.setFloatValues(imageView.scaleX + 0.1f)
                objectAnimatorY.setFloatValues(imageView.scaleY + 0.1f)
                mViewModel.scaleFactor += 0.1f
                objectAnimatorX.start()
                objectAnimatorY.start()
            }
        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = MyListAdapter(false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.submitList(iconsList())
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).toolbarLayout?.title = getString(R.string.scale)
        (requireActivity() as MainActivity).toolbarIconImageView?.setImageResource(R.drawable.ic_looks_two)
    }

    private fun iconsList(): List<Int> {
        val array = intArrayOf(
            R.drawable.ic_1,
            R.drawable.ic_2,
            R.drawable.ic_3,
            R.drawable.ic_4,
            R.drawable.ic_5,
            R.drawable.ic_6,
            R.drawable.ic_7,
            R.drawable.ic_8,
            R.drawable.ic_9
        )
        return Array(50) {
            array.random()
        }.toList()
    }
}