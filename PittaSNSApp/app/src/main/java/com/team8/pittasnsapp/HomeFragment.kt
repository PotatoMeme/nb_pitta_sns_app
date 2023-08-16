package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.adapter.PostRecyclerView
import com.team8.pittasnsapp.adapter.ProfileRecyclerView
import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()

        private const val TAG = "HomeFragment"
        private var count1 = 0
        private var count2 = 0
    }
    init {

        Log.d(TAG, "init: ${count1++}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        Log.d(TAG, "initView: ${count2++}")
        view.findViewById<RecyclerView>(R.id.profile_recycler_view).apply {
            adapter = ProfileRecyclerView { pos ->
                val intent: Intent =
                    Intent(this@HomeFragment.context, UserDetailActivity::class.java)
                startActivity(intent)
            }.apply {
                repeat(20) { addUser(User(it, "sampleUser$it")) }
                notifyDataSetChanged()
            }
            layoutManager =
                LinearLayoutManager(
                    this@HomeFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

        view.findViewById<RecyclerView>(R.id.post_recycler_view).apply {
            adapter = PostRecyclerView { pos ->
                val intent: Intent =
                    Intent(this@HomeFragment.context, PostDetailActivity::class.java)
                startActivity(intent)
            }.apply {
                repeat(100) {
                    addPost(
                        Post(
                            it,
                            User(it, "sampleUser$it"),
                            "sample description $it".repeat(10),
                            "2023-08-14"
                        )
                    )
                }
                notifyDataSetChanged()
            }

            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }
}