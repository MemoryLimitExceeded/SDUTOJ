package com.sdutacm.sdutoj.item.bean

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class ContestBean(
    val cid: Int = 0,
    val name: String = "",
    val type: Int = 0,
    val start_time: String = "",
    val end_time: String = "",
    val register_start_time: String = "",
    val register_end_time: String = "",
    val description: String = "",
    val problem: ArrayList<ProblemItemBean> = ArrayList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readArrayList(ProblemItemBean::class.java.classLoader) as ArrayList<ProblemItemBean>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cid)
        parcel.writeString(name)
        parcel.writeInt(type)
        parcel.writeString(start_time)
        parcel.writeString(end_time)
        parcel.writeString(register_start_time)
        parcel.writeString(register_end_time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContestBean> {
        override fun createFromParcel(parcel: Parcel): ContestBean {
            return ContestBean(parcel)
        }

        override fun newArray(size: Int): Array<ContestBean?> {
            return arrayOfNulls(size)
        }
    }

}

data class ProblemItemBean constructor(
    val pid: Int,
    val title: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pid)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProblemItemBean> {
        override fun createFromParcel(parcel: Parcel): ProblemItemBean {
            return ProblemItemBean(parcel)
        }

        override fun newArray(size: Int): Array<ProblemItemBean?> {
            return arrayOfNulls(size)
        }
    }

}