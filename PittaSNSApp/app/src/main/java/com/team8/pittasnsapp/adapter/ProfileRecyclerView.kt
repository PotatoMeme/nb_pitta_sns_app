package com.team8.pittasnsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.R
import com.team8.pittasnsapp.model.User

class ProfileRecyclerView(
    val itemClickFunction: (userId: Int) -> Unit,
) : RecyclerView.Adapter<ProfileRecyclerView.ViewHolder>() {
    private val profileArrayList: ArrayList<User> = ArrayList()

    fun addUser(user: User) {
        profileArrayList.add(user)
        notifyDataSetChanged()
    }
    fun addAllUser(users: List<User>) {
        profileArrayList.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            view.findViewById<LinearLayout>(R.id.profile_layout)
                .setOnClickListener { itemClickFunction(profileArrayList[pos].id) }

            //view.findViewById<ImageView>(R.id.profile_image_view).drawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return profileArrayList.size
    }
}