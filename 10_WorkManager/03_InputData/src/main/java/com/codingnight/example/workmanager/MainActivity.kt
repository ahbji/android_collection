package com.codingnight.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("IN_KEY" to "Work A"))
            .build()
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
            Log.i("MainActivity", "state: " + it.state)
            if (it.state == WorkInfo.State.SUCCEEDED)
                Log.i("MainActivity", "outputData: " + it.outputData.getString("OUT_KEY"))
        })
    }
}