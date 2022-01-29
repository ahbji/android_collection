package com.codingnight.android.videoview

import android.media.PlaybackParams
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.codingnight.android.videoview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoView = binding.videoView
//        binding.videoView.setVideoPath("android.resource://$packageName/${R.raw.video}")
//        videoView.setVideoURI(Uri.parse("https://stream7.iqilu.com/10339/upload_transcode/202002/09/20200209105011F0zPoYzHry.mp4"))
        videoView.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.video}"))
        videoView.setMediaController(MediaController(this))
//        videoView.start()


        videoView.setOnPreparedListener {
            it.seekTo(3000)
            // 最低支持 API 23
            it.playbackParams = PlaybackParams().apply {
                speed = 2f // 隐式调用 start
                pitch = 2f
            }
            it.isLooping = true
            it.start()
        }
    }
}