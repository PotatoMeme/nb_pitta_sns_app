package com.team8.pittasnsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyboardShortcutGroup
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.team8.pittasnsapp.adapter.StaggeredPostRecyclerViewAdapter
import com.team8.pittasnsapp.model.User


class UserDetailActivity : AppCompatActivity() {
    companion object {
        private const val HOME = 16908332
        private const val TAG = "UserDetailActivity"
    }

    private var userId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        settings()
        initViews()
    }

    private fun settings() {
        userId = intent.getIntExtra(Key.INTENT_USER_ID, 0)
        Log.d(TAG, "settings: $userId")
    }

    private fun initViews() {
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (userId != null) {
            val currentUser: User = SampleData.userArrayList.first { it.id == userId }
            supportActionBar?.title = currentUser.name
            findViewById<TextView>(R.id.name_text_view).text = currentUser.name
            val imageView: ImageView = findViewById<ImageView>(R.id.profile_image_view)

            Glide.with(this)
                .load(currentUser.userImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)

            findViewById<TextView>(R.id.message_text_view).text = currentUser.message

            findViewById<RecyclerView>(R.id.post_recycler_view).apply {
                adapter = StaggeredPostRecyclerViewAdapter { postId ->
                    val intent: Intent =
                        Intent(this@UserDetailActivity, PostDetailActivity::class.java)
                    intent.putExtra(Key.INTENT_USER_ID, userId)
                    intent.putExtra(Key.INTENT_POST_ID, postId)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.none)
                }.apply {
                    addAllPost(SampleData.postArrayList.filter { it.user.id == userId })
                }
                val staggeredGridLayoutManager =
                    StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                layoutManager = staggeredGridLayoutManager
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId} ")
        when(item.itemId){
            HOME -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}