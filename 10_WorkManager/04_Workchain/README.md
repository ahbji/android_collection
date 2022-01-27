# 链式调度 WorkRequest

WorkManager 支持链式调用 WorkRequest 。

```kotlin
val workRequestA = createWork("Work A")
val workRequestB = createWork("Work B")
workManager.beginWith(workRequestA)
    .then(workRequestB)
    .enqueue()
```

被链式调用的 WorkRequest 也支持应用重新运行后重新调度。
> 已经执行完成的 WrokRequest 不会被重新调度。
