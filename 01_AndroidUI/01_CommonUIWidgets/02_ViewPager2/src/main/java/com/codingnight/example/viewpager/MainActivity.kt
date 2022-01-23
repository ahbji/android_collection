package com.codingnight.example.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codingnight.example.viewpager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewPager = binding.viewpager
        val tabLayout = binding.tablayout
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int) =
                when (position) {
                    1 -> FirstFragment.newInstance()
                    2 -> SecondFragment.newInstance()
                    else -> ThirdFragment.newInstance() // position 0
                }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                // 从左到右
                0 -> tab.text = resources.getString(R.string.translate)
                1 -> tab.text = getString(R.string.rotate)
                2 -> tab.text = resources.getString(R.string.scale)
            }
        }.attach()
    }
}