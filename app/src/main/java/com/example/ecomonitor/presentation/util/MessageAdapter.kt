package com.example.ecomonitor.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomonitor.R
import com.example.ecomonitor.domain.model.Message

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class MessageAdapter : ListAdapter<Message, MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = when (viewType) {
            MESSAGE_TYPE_OWN -> R.layout.own_message
            else -> R.layout.other_messsage
        }
        val itemView = inflater.inflate(layoutId, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.isSentByMe) MESSAGE_TYPE_OWN else MESSAGE_TYPE_OTHER
    }

    companion object {
        private const val MESSAGE_TYPE_OWN = 0
        private const val MESSAGE_TYPE_OTHER = 1
    }
}

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val messageBodyTextView: TextView = itemView.findViewById(R.id.MessageBody)

    fun bind(message: Message) {
        messageBodyTextView.text = message.value
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.messageId == newItem.messageId
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}
