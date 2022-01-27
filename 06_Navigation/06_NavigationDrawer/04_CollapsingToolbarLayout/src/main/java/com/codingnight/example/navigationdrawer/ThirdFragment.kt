package com.codingnight.example.navigationdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.codingnight.example.navigationdrawer.databinding.ActivityMainBinding
import com.codingnight.example.navigationdrawer.databinding.FirstFragmentBinding
import com.codingnight.example.navigationdrawer.databinding.ThirdFragmentBinding
import java.util.*

class ThirdFragment : Fragment() {
    private lateinit var mViewModel: ThirdViewModel
    private lateinit var imageView: ImageView
    private lateinit var viewPager: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ThirdFragmentBinding.inflate(inflater)
        imageView = binding.imageView
        viewPager = binding.viewPager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity()).get(
            ThirdViewModel::class.java
        )
        imageView.x = imageView.x + mViewModel.dX
        // TODO: Use the ViewModel
        val objectAnimator = ObjectAnimator.ofFloat(imageView, "x", 0f, 0f)
        objectAnimator.duration = 500
        imageView.setOnClickListener {
            if (!objectAnimator.isRunning) {
                val dx: Float = if (Random().nextBoolean()) 100F else (-100).toFloat()
                objectAnimator.setFloatValues(imageView.x, imageView.x + dx)
                mViewModel.dX += dx
                objectAnimator.start()
            }
        }

        val adapter = MyListAdapter(true)
        viewPager.adapter = adapter
        adapter.submitList(iconsList())
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

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).toolbarLayout?.title = getString(R.string.translate)
        (requireActivity() as MainActivity).toolbarIconImageView?.setImageResource(R.drawable.ic_looks_three)
    }
}