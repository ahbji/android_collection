# LifecycleObserver

LifecycleObserver 接口可以为某个类的实例成为一个 Activity 生命周期的观察者，当 Activity 生命周期发生变化时，观察者会收到通知。

例如一个需要获取位置信息的 Activity ，通常需要在 onResume() 调用时获取 LocationManager 服务、注册 LocationListener ，在 onPause() 调用时移除已注册的 LocationListener、清理 LocationManager 服务。

使用 LifecycleObserver 接口，可以将上述操作与 Activity 的生命周期回调解耦。