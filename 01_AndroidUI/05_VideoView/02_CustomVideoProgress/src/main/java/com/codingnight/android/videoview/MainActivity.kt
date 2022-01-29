package com.codingnight.android.videoview

import android.media.PlaybackParams
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.lifecycle.lifecycleScope
import com.codingnight.android.videoview.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoView = binding.videoView
        videoView.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.video}"))
        videoView.setOnPreparedListener {
            Log.i("VideoView", "onCreate: $it")
            binding.progressBar.max = it.duration
            binding.progressBar2.visibility = View.INVISIBLE
            it.isLooping = true
            it.start()
        }
        lifecycleScope.launch {
            while (true) {
                if (videoView.isPlaying) {
                    binding.progressBar.progress = videoView.currentPosition
                }
                delay(500)
            }
        }
    }
}