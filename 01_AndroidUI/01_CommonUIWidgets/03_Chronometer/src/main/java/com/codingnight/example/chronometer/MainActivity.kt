package com.codingnight.example.chronometer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.codingnight.example.chronometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var meter: Chronometer
    lateinit var viewModel: MeterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        meter = binding.meter
        viewModel = ViewModelProvider(this).get(MeterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onPause() {
        super.onPause()
        viewModel.setElapsedTime(meter.base)
        meter.stop()
    }

    override fun onResume() {
        super.onResume()
        meter.base = viewModel.getBase()
        meter.start()
    }
}