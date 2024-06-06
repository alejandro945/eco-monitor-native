package com.example.ecomonitor.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.databinding.ActivityChatBinding
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.presentation.util.MessageAdapter
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.ChatViewModel
import com.example.ecomonitor.presentation.viewmodel.ProfileViewModel

class ChatActivity : AppCompatActivity() {
    //private val chatId: String by extra("chat_id") // Get chat ID from intent
    private val viewModel: ChatViewModel by viewModels() // Inject chat ID

    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageAdapter = MessageAdapter()

        binding.MessageListView.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(this@ChatActivity)
            // Optionally, you can add item decorations or other configurations here
        }

        viewModel.messages.observe(this) { messages ->
            messageAdapter.submitList(messages)
        }

        binding.GoBackBtn.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        binding.SendMessageBtn.setOnClickListener {
            val content = binding.Message.text.toString()
            if (content.isNotBlank()) {
                viewModel.sendMessage(content)
                binding.Message.text.clear()
            }
        }
    }
}