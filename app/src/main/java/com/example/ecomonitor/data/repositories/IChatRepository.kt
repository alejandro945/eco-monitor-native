package com.example.ecomonitor.data.repositories

import com.example.ecomonitor.domain.model.Chat
import com.example.ecomonitor.domain.model.Measurement

interface IChatRepository {

    suspend fun getChat() : Chat

    suspend fun createChat(emisorId: String, receiverId: String ): Chat

    fun observe(listener: (Chat) -> Unit)
}