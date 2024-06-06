package com.example.ecomonitor.domain.model

data class Message (
    val messageId: String = "",
    val chatId: String = "",
    val value : String = "",
    val userId : String = "",
    val date: String = "",
    val isSentByMe: Boolean = false // Flag to indicate if the message was sent by the user

)