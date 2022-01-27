package com.codingnight.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.i("MyWork", "doWork: started")
        Thread.sleep(3000)
        Log.i("MyWork", "doWork: finished")
        return Result.success()
    }
}