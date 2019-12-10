package com.sdutacm.sdutoj.item.bean

import android.os.Parcel
import android.os.Parcelable

data class ProblemBean constructor(
    val pid: Int = 0,
    val title: String = "",
    val time_limit: Int = 0,
    val memory_limit: Int = 0,
    val description: String = "",
    val input: String = "",
    val output: String = "",
    val sample_input: String = "",
    val sample_output: String = "",
    val hint: String = "",
    val source: String = "",
    val added_time: String = "",
    val accepted: Int = 0,
    val submission: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pid)
        parcel.writeString(title)
        parcel.writeInt(time_limit)
        parcel.writeInt(memory_limit)
        parcel.writeString(description)
        parcel.writeString(input)
        parcel.writeString(output)
        parcel.writeString(sample_input)
        parcel.writeString(sample_output)
        parcel.writeString(hint)
        parcel.writeString(source)
        parcel.writeString(added_time)
        parcel.writeInt(accepted)
        parcel.writeInt(submission)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProblemBean> {
        override fun createFromParcel(parcel: Parcel): ProblemBean {
            return ProblemBean(parcel)
        }

        override fun newArray(size: Int): Array<ProblemBean?> {
            return arrayOfNulls(size)
        }
    }
}
