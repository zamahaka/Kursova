package com.nulp.zamahaka.kursova.mvp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Ura on 26.03.2017.
 */
data class Conversation(@SerializedName("id") val mId: Int = -1,
                        @SerializedName("title") val mTitle: String = "",
                        @SerializedName("avatar") val mAvatar: String = "",
                        @SerializedName("unread") val mUnread: Int = -1,
                        @SerializedName("displayedMessage") val mDisplayedMessage: Message = Message()) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Conversation> = object : Parcelable.Creator<Conversation> {
            override fun createFromParcel(source: Parcel): Conversation = Conversation(source)
            override fun newArray(size: Int): Array<Conversation?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), source.readString(), source.readInt(), source.readParcelable<Message>(Message::class.java.classLoader))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(mId)
        dest?.writeString(mTitle)
        dest?.writeString(mAvatar)
        dest?.writeInt(mUnread)
        dest?.writeParcelable(mDisplayedMessage, 0)
    }
}