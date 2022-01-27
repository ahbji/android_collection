# Worker 参数

WorkRequest 可以通过 setInputData 向 Worker 传参

```kotlin
val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
    .setInputData(workDataOf("IN_KEY" to "Work A"))
    .build()

val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
    .setInputData(workDataOf(Pair("IN_KEY", "Work A")))
    .build()
```

在 Worker 中通过 getInput 获取参数

```kotlin
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString("IN_KEY")
        Log.i("MyWork", "doWork: started, name: $name")
        Thread.sleep(3000)
        Log.i("MyWork", "doWork: finished")
        return Result.success()
    }
}
```

另外 Worker 也可以通过 Result 接口回传参数

```kotlin
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success(workDataOf("OUT_KEY" to "$name OUT"))
    }
}
```

workManager 可以通过 workRequest.id 获取包装 WorkInfo 的 LiveData ，通过为这个 LiveData 设置观察者可以来获取 Worker 传回的信息。

```kotlin
    workManager.enqueue(workRequest)
    workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this, Observer {
        Log.i("MainActivity", "state: " + it.state)
        if (it.state == WorkInfo.State.SUCCEEDED)
            Log.i("MainActivity", "outputData: " + it.outputData.getString("OUT_KEY"))
    })
```
