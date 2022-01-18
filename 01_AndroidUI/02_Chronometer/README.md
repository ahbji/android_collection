# Chronometer

Chronometer 是一个计时器控件，是 TextView 的子类

## count up 计时模式

默认为 count up 计时模式，使用如下所示。
```kotlin
chronometer.base = SystemClock.elapsedRealtime()
chronometer.start()
    ...
chronometer.stop()
```
1. 首先通过 setBase() 设置初始时间
2. 当调用 start 开始计时，通过每秒 post 一次计时任务。
3. 在计时任务中，取当前时间与初始时间之差，并将差值进行 setText
4. 当调用 stop ，移除计时任务。
