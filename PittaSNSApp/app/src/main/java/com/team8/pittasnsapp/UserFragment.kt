package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.team8.pittasnsapp.adapter.StaggeredPostRecyclerViewAdapter
import com.team8.pittasnsapp.model.User

private const val ARG_PARAM1 = "current_user_id"
class UserFragment : Fragment() {
    companion object {
        fun newInstance(currentUserId : Int) = UserFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, currentUserId)
            }
        }
    }

    private var currentUserId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentUserId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        if (currentUserId != null){
            val currentUser: User = SampleData.userArrayList.first { it.id == currentUserId }
            val imageView: ImageView = view.findViewById<ImageView>(R.id.profile_image_view)

            Glide.with(this)
                .load(currentUser.userImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
            view.findViewById<TextView>(R.id.name_text_view).text = currentUser.name
            view.findViewById<TextView>(R.id.message_text_view).text = currentUser.message

            view.findViewById<RecyclerView>(R.id.post_recycler_view).apply {
                adapter = StaggeredPostRecyclerViewAdapter { postId ->
                    val intent: Intent =
                        Intent(this@UserFragment.context, PostDetailActivity::class.java)
                    intent.putExtra(Key.INTENT_USER_ID, currentUserId)
                    intent.putExtra(Key.INTENT_POST_ID, postId)
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
                }.apply {
                    addAllPost(SampleData.postArrayList.filter { it.user.id == currentUserId })
                }
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                layoutManager = staggeredGridLayoutManager
            }
        }
    }
}