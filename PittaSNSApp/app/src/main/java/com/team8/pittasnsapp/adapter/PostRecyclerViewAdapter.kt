package com.team8.pittasnsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team8.pittasnsapp.R
import com.team8.pittasnsapp.model.Post

class PostRecyclerViewAdapter(
    val itemClickFunction: (postId: Int) -> Unit,
) : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>() {
    private val postArrayList: ArrayList<Post> = ArrayList()

    fun addPost(post: Post) {
        postArrayList.add(post)
        notifyDataSetChanged()
    }

    fun addAllPost(posts:List<Post>){
        postArrayList.addAll(posts)
        notifyDataSetChanged()
    }

    fun changeAllPost(posts:List<Post>){
        postArrayList.clear()
        postArrayList.addAll(posts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            val currentItem = postArrayList[pos]

            view.setOnClickListener { itemClickFunction(currentItem.id) }

            view.findViewById<TextView>(R.id.title_text_view).text = currentItem.title
            view.findViewById<TextView>(R.id.profile_text_view).text = currentItem.user.name
            view.findViewById<TextView>(R.id.post_description_text_view).text =
                currentItem.description

            val profileImageView : ImageView = view.findViewById(R.id.profile_image_view)
            Glide.with(view)
                .load(currentItem.user.userImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(profileImageView)

            val postImageView: ImageView = view.findViewById(R.id.post_image_view)
            Glide.with(view)
                .load(postArrayList[pos].postImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(postImageView)
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