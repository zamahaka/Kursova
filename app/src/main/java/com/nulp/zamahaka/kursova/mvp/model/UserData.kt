package com.nulp.zamahaka.kursova.mvp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Ura on 26.03.2017.
 */
val DEFAULT_INT = -1
val DEFAULT_STRING = ""
val DEFAULT_LONG = -1L
val DEFAULT_GENDER = Gender.NONE

data class User(
        @SerializedName("id") val mId: Int = DEFAULT_INT,
        @SerializedName("name") val mName: String = DEFAULT_STRING,
        @SerializedName("surName") val mSurName: String = DEFAULT_STRING,
        @SerializedName("avatar") val mAvatar: String = DEFAULT_STRING,
        @SerializedName("created") val mCreated: Long = DEFAULT_LONG,
        @SerializedName("gender") val mGender: Gender = DEFAULT_GENDER,
        @SerializedName("lastSeen") val mLastSeen: Long = DEFAULT_LONG,
        @SerializedName("place") val mPlace: String = DEFAULT_STRING,
        @SerializedName("education") val mEducation: String = DEFAULT_STRING
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString(), source.readString(), source.readLong(), Gender.values()[source.readInt()], source.readLong(), source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(mId)
        dest?.writeString(mName)
        dest?.writeString(mSurName)
        dest?.writeString(mAvatar)
        dest?.writeLong(mCreated)
        dest?.writeInt(mGender.ordinal)
        dest?.writeLong(mLastSeen)
        dest?.writeString(mPlace)
        dest?.writeString(mEducation)
    }
}

enum class Gender {
    @SerializedName("none") NONE,
    @SerializedName("male") MALE,
    @SerializedName("female") FEMALE
}