package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.IUserRepository
import com.example.ecomonitor.domain.model.Message
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ChatViewModel(
    private val userRepository: IUserRepository = FirebaseUserRepository()
) : ViewModel(

) {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val botMessageList = listOf(
        "Claro, ¡estoy aquí para ayudarte! Cuentame más del error que tienes",
        "Ya intentaste reiniciar la aplicación?",
        "Voy a contactarte con un especialista para que te ayude...",
        "Te contactarán lo más pronto posible",
        "Fue un placer haberte ayudado, ¡hasta la próxima!"
    )

    private var botMessageIndex = 0

    init {
        val initialMessages = listOf(
            Message("", value = "Hola, ¿cómo puedo ayudarte?", userId = "Bot", isSentByMe = false),
        )
        _messages.value = initialMessages
    }

    fun sendMessage(content: String) {
        val newMessage = Message("", "", content,userRepository.getUserId(), Date().toString(), true)
        val updatedMessages = _messages.value!!.toMutableList()
        updatedMessages.add(newMessage)


        //_messages.value = updatedMessages
        _messages.postValue(updatedMessages)

        if (botMessageIndex >= botMessageList.size) {
            botMessageIndex = 0
        }
        //Simulate a delayed response from the other side
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            val botMessage = Message("", "",
                botMessageList[botMessageIndex], "Bot", Date().toString(), false)
            val latestMessages = _messages.value!!.toMutableList()
            latestMessages.add(botMessage)
            //_messages.value = latestMessages
            _messages.postValue(latestMessages)
        }

        botMessageIndex = (botMessageIndex + 1) % botMessageList.size
    }
}