package com.codeingnight.android.sharedpreference

import android.content.Context
import android.util.Log

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