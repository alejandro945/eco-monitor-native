package com.example.ecomonitor.presentation.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomonitor.R
import com.example.ecomonitor.domain.model.Profile
import com.example.ecomonitor.databinding.UserListItemBinding
import com.example.ecomonitor.domain.model.User

class UserAdapter(var data: ArrayList<Profile>) : RecyclerView.Adapter<UserVH>() {

    //Listeners
    var chatButtonAction: ((Profile) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return UserVH(view)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(newData: List<Profile>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()  // Asegúrate de llamar a notifyDataSetChanged
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        Log.d("UserAdapter", "Binding user at position $position: ${data[position]}")
        holder.name.text = data[position].name
        holder.email.text = data[position].email
        holder.role.text = data[position].role.toString()
        holder.chatButton.setOnClickListener {
            chatButtonAction?.let {
                it(data[position])
            }
        }

    }


}

class UserVH(root: View) : RecyclerView.ViewHolder(root) {
    private val binding = UserListItemBinding.bind(root)
    val name = binding.nameTV
    val email = binding.emailTV
    val role = binding.roleTV
    val chatButton = binding.chatButton
}