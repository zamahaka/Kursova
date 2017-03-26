package com.nulp.zamahaka.kursova.mvp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nulp.zamahaka.kursova.R
import com.nulp.zamahaka.kursova.listener.ConversationListener
import com.nulp.zamahaka.kursova.load
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import kotlinx.android.synthetic.main.item_conversation_list.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ura on 26.03.2017.
 */
class ConversationListAdapter(private val mListener: ConversationListener) : BaseAdapter<Conversation>() {

    override fun onBindViewHolder(holder: BaseViewHolder<Conversation>, position: Int) =
            holder.bind(mItems[position])


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConversationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversation_list, parent, false))

    override fun getItemCount() = mItems.size

    class ConversationViewHolder(view: View) : BaseViewHolder<Conversation>(view) {
        override fun bind(item: Conversation) {
            itemView.imageAvatar.load(item.mAvatar)
            if (item.mAvatar != item.mDisplayedMessage.mAuthor.mAvatar) {
                itemView.imageSenderAvatar.visibility = View.VISIBLE
                itemView.imageSenderAvatar.load(item.mDisplayedMessage.mAuthor.mAvatar,
                        R.drawable.ic_person)
            } else {
                itemView.imageSenderAvatar.setImageDrawable(null)
                itemView.imageSenderAvatar.visibility = View.VISIBLE
            }

            itemView.title.text = item.mTitle
            itemView.textCreated.text = getCreatedText(item.mDisplayedMessage.mCreated)
            itemView.textMessage.text = item.mDisplayedMessage.mText

            if (item.mUnread > 0) {
                itemView.textUnread.text = item.mUnread.toString()
                itemView.textUnread.visibility = View.VISIBLE
            } else {
                itemView.textUnread.visibility = View.GONE
            }
        }

        // TODO: 26.03.2017 add support for not today and not this year
        private fun getCreatedText(created: Long): String = SimpleDateFormat("HH:mm").format(Date(created))
    }

}