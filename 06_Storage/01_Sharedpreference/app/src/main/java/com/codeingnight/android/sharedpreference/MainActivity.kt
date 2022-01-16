package com.codeingnight.android.sharedpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var shp = getPreferences(Context.MODE_PRIVATE)

//        var shp = getSharedPreferences(getString(R.string.file_key), MODE_PRIVATE)
//        with (shp.edit()) {
//            putInt("NUMBER", 100)
//            apply()
//        }
//        var x = getSharedPreferences(getString(R.string.file_key), MODE_PRIVATE).getInt("NUMBER", 0)
//        var TAG = "MyLog"
//        Log.d(TAG, "onCreate: " + x)

        val myData = MyData(applicationContext)
        myData.save()
        myData.load()
    }
}