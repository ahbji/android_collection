# Shared preference

## 封装 getPreference

```kotlin
class MyData(val _context: Context) {
    fun save() {
        with(_context) {
            val shp = getPreferences(
                Context.MODE_PRIVATE
            )
            with(shp.edit()) {
                putInt(getString(R.string.number_key), 200)
                apply()
            }
        }
    }

    fun load() {
        with(_context) {
            val x = getPreferences(
                Context.MODE_PRIVATE
            ).getInt(getString(R.string.number_key), 0)
            var TAG = "MyLog"
            Log.d(TAG, "onCreate: " + x)
        }
    }
}
```

## 封装 getSharedPreferences
```kotlin
class MyData(val _context: Context) {
    fun save() {
        with(_context) {
            val shp = getSharedPreferences(
                getString(R.string.file_key),
                Context.MODE_PRIVATE
            )
            with(shp.edit()) {
                putInt(getString(R.string.number_key), 200)
                apply()
            }
        }
    }

    fun load() {
        with(_context) {
            val x = getSharedPreferences(
                getString(R.string.file_key),
                Context.MODE_PRIVATE
            ).getInt(getString(R.string.number_key), 0)
            var TAG = "MyLog"
            Log.d(TAG, "onCreate: " + x)
        }
    }
}
```

## 使用
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ...
        val myData = MyData(applicationContext)
        myData.save()
        myData.load()
    }
}
```
