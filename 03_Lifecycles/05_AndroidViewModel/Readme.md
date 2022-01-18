# AndroidViewModel

AndroidViewModel 对 ViewModel 进行了封装，同时需要向构造函数传参 Application。

通过 Application 可以在 ViewModel 中通过 getApplication 访问。

```kotlin
class MyViewModel(application: Application) : AndroidViewModel(application) {
    ...
    getApplication<Application>().getString(R.string.shp_name)
    getApplication<Application>().getSharedPreferences(shpName, Context.MODE_PRIVATE)
}
```