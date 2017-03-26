package com.nulp.zamahaka.kursova.mvp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Ura on 26.03.2017.
 */
data class Message(
        @SerializedName("id") val mId: Int = -1,
        @SerializedName("conversationId") val mConversationId: Int = -1,
        @SerializedName("text") val mText: String = "",
        @SerializedName("author") val mAuthor: User = User(),
        @SerializedName("created") val mCreated: Long = -1,
        @SerializedName("tempId") val mTempId: String = ""
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Message> = object : Parcelable.Creator<Message> {
            override fun createFromParcel(source: Parcel): Message = Message(source)
            override fun newArray(size: Int): Array<Message?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt(), source.readString(), source.readParcelable<User>(User::class.java.classLoader), source.readLong(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(mId)
        dest?.writeInt(mConversationId)
        dest?.writeString(mText)
        dest?.writeParcelable(mAuthor, 0)
        dest?.writeLong(mCreated)
        dest?.writeString(mTempId)
    }
}