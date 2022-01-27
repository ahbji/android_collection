package com.codingnight.example.workmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

class MainActivity : AppCompatActivity() {
    private val workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        val workRequestA = createWork("Work A")
        val workRequestB = createWork("Work B")
        val workRequestC = createWork("Work C")
        workManager.beginWith(workRequestA)
            .then(workRequestB)
            .then(workRequestC)
            .enqueue()
    }

    private fun createWork(name: String): OneTimeWorkRequest {
        val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(workDataOf("IN_KEY" to name))
            .build()
        return workRequest
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}