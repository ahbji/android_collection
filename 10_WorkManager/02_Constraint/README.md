# WorkManager Constraint

可以为 WorkRequest 定义一个约束条件，当约束条件满足时，WorkManager 才调度 WorkRequest 。

```kotlin
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .build()
    val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
        .setConstraints(constraints)
        .build()
    workManager.enqueue(workRequest)
```
