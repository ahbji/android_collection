package com.codingnight.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString("IN_KEY")
        Log.i("MyWork", "doWork: started, name: $name")
        Thread.sleep(3000)
        Log.i("MyWork", "doWork: finished")
        return Result.success(workDataOf("OUT_KEY" to "$name OUT"))
    }
}