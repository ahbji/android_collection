package com.codingnight.android.counter

import android.os.Parcel
import android.os.Parcelable

class MainModel() : Parcelable {
    var number = 0

    fun increment() {
        number++
    }

    constructor(input: Parcel) : this() {
        number = input.readInt()
    }

    override fun writeToParcel(output: Parcel, flags: Int) {
        output.writeInt(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainModel> {
        override fun createFromParcel(input: Parcel): MainModel {
            return MainModel(input)
        }

        override fun newArray(size: Int): Array<MainModel?> {
            return arrayOfNulls(size)
        }
    }
}