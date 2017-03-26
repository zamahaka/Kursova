package com.nulp.zamahaka.kursova.mvp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Ura on 26.03.2017.
 */
data class User(
        @SerializedName("id") val mId: Int = -1,
        @SerializedName("name") val mName: String = "",
        @SerializedName("surName") val mSurName: String = "",
        @SerializedName("avatar") val mAvatar: String = "",
        @SerializedName("created") val mCreated: Long = -1,
        @SerializedName("conversations") val mConversationIds: List<Int> = ArrayList(),
        @SerializedName("gender") val mGender: Int = -1,
        @SerializedName("lastSeen") val mLastSeen: Long = -1,
        @SerializedName("place") val mPlace: String = "",
        @SerializedName("education") val mEducation: String = ""
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString(),
            source.readString(), source.readLong(),
            ArrayList<Int>().apply{ source.readList(this, Int::class.java.classLoader) },
            source.readInt(), source.readLong(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(mId)
        dest?.writeString(mName)
        dest?.writeString(mSurName)
        dest?.writeString(mAvatar)
        dest?.writeLong(mCreated)
        dest?.writeList(mConversationIds)
        dest?.writeInt(mGender)
        dest?.writeLong(mLastSeen)
        dest?.writeString(mPlace)
        dest?.writeString(mEducation)
    }
}