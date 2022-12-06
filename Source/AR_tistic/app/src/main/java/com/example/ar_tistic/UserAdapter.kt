package com.example.ar_tistic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classlib.User

class UserAdapter(val UserAfficher: ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view ,parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = UserAfficher.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text=UserAfficher[position].email
        }

        class ViewHolder(val elementDeListe : View) : RecyclerView.ViewHolder(elementDeListe)
        {
            val textView: TextView = itemView.findViewById(R.id.testViewUser)
        }

    }