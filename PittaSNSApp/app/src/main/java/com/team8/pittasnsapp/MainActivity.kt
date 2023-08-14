package com.team8.pittasnsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.adapter.PostRecyclerView
import com.team8.pittasnsapp.adapter.ProfileRecyclerView
import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<RecyclerView>(R.id.profile_recycler_view).apply {
            adapter = ProfileRecyclerView { pos ->
                val intent: Intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                startActivity(intent)
            }.apply {
                repeat(20){ addUser(User(it,"sampleUser$it")) }
                notifyDataSetChanged()
            }
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        findViewById<RecyclerView>(R.id.post_recycler_view).apply {
            adapter = PostRecyclerView { pos ->
                val intent: Intent = Intent(this@MainActivity, PostDetailActivity::class.java)
                startActivity(intent)
            }.apply {
                repeat(100){ addPost(Post(it,User(it,"sampleUser$it"),"sample description $it".repeat(10),"2023-08-14")) }
                notifyDataSetChanged()
            }

            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}