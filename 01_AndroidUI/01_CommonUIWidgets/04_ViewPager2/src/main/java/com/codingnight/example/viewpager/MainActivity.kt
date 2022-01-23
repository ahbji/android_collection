package com.codingnight.example.viewpager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.codingnight.example.viewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var layout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var dots: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layout = binding.dctsContainer
        viewPager = binding.viewPager

        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int) =
                when (position) {
                    1 -> FirstFragment.newInstance()
                    2 -> SecondFragment.newInstance()
                    else -> ThirdFragment.newInstance() // position 0
                }
        }

        dots = arrayOf(
            setIndicators(),
            setIndicators(),
            setIndicators(),
        )

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDots(position)
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDots(position: Int) {
        dots.forEachIndexed { index, textView ->
            if (index == position)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
                } else {
                    textView.setTextColor(resources.getColor(R.color.black))
                }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextColor(ContextCompat.getColor(applicationContext, R.color.grey))
                } else {
                    textView.setTextColor(resources.getColor(R.color.grey))
                }
            }
        }
    }

    private fun setIndicators(): TextView {
        val textView = TextView(this)
        textView.text = Html.fromHtml("&#9679")
        textView.textSize = 18f
        layout.addView(textView)
        return textView
    }
}