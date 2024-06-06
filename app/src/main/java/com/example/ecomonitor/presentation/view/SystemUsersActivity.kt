package com.example.ecomonitor.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomonitor.databinding.ActivityProfileBinding
import com.example.ecomonitor.databinding.ActivitySystemUsersBinding
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.SuccessStatus
import com.example.ecomonitor.domain.model.TransactionStatus.ErrorStatus
import com.example.ecomonitor.domain.model.TransactionStatus.LoadingStatus
import com.example.ecomonitor.presentation.util.UIUtil
import com.example.ecomonitor.presentation.viewmodel.ProfileViewModel
import com.example.ecomonitor.presentation.viewmodel.SystemUsersViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.presentation.util.UserAdapter

class SystemUsersActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySystemUsersBinding.inflate(layoutInflater) }
    private val viewModel: SystemUsersViewModel by viewModels()
    private val adapter = UserAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter

        adapter.chatButtonAction = {
            // TODO: Change to ChatActivity
            // Change to ChatActivity
            // And change putextra to receiver_id and messeger_id
            profile -> val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("profile_id", profile.id)
            intent.putExtra("profile_name", profile.name)
            intent.putExtra("profile_email", profile.email)
            intent.putExtra("profile_role", profile.role)
            startActivity(intent)
        }

        viewModel.users.observe(this) { users ->
            Log.d("SystemUsersActivity", "Users list updated: ${users.size}")  // Añade un log aquí para verificar
            adapter.updateData(users)
        }

        viewModel.fetchUsers()




    }


}