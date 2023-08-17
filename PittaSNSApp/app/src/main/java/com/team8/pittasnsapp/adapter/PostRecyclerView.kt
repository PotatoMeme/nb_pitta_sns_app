package com.team8.pittasnsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.R
import com.team8.pittasnsapp.model.Post

class PostRecyclerView(
    val itemClickFunction: (postId: Int) -> Unit,
) : RecyclerView.Adapter<PostRecyclerView.ViewHolder>() {
    private val postArrayList: ArrayList<Post> = ArrayList()

    fun addPost(post: Post) {
        postArrayList.add(post)
        notifyDataSetChanged()
    }

    fun addAllPost(posts:List<Post>){
        postArrayList.addAll(posts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            view.setOnClickListener { itemClickFunction(postArrayList[pos].id) }

            view.findViewById<TextView>(R.id.profile_text_view).text = postArrayList[pos].user.name
            view.findViewById<TextView>(R.id.post_description_text_view).text =
                postArrayList[pos].description

        //view.findViewById<ImageView>(R.id.post_image_view).drawable
            //view.findViewById<ImageView>(R.id.profile_image_view).drawable
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return postArrayList.size
    }
}