package com.momentousmoss.tz_devices_messages.ui.messages

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.momentousmoss.tz_devices_messages.R
import com.momentousmoss.tz_devices_messages.databinding.ItemMessageRowBinding
import com.momentousmoss.tz_devices_messages.dto.MessageDto

class MessagesAdapter(
    private val messagesList: MutableList<MessageDto?>
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    class MessageViewHolder internal constructor(
        binding: ItemMessageRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val messageId: TextView = binding.id
        val messageTime: TextView = binding.time
        val messageDate: TextView = binding.date
        val messageAuthor: TextView = binding.author
        val messageText: TextView = binding.text
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageViewHolder {
        return MessageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_message_row,
                viewGroup,
                false
            )
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(messageViewHolder: MessageViewHolder, i: Int) {
        messagesList[i]?.apply {
            val messageData = this
            messageViewHolder.apply {
                messageId.text = messageData.id.toString()
                messageTime.text = messageData.time
                messageDate.text = messageData.date
                messageAuthor.text = messageData.author
                messageText.text = messageData.text
            }
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }
}