package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User

class PostDetailActivity : AppCompatActivity() {

    companion object {
        private const val HOME = 16908332
        private const val TAG = "PostDetailActivity"
    }

    private var postId: Int? = null
    private var currentUserId : Int? = null
    private var isSameUser : Boolean = false

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { actvityResult: ActivityResult ->
        if (actvityResult.resultCode == Key.RESULT_OK_POST_UPDATE) {
            val title: String? = actvityResult.data?.getStringExtra(Key.INTENT_POST_TITLE)
            val description: String? =
                actvityResult.data?.getStringExtra(Key.INTENT_POST_DESCRIPTION)

            if (title != null && description != null) {
                SampleData.changePost(postId!!,title,description)
                startActivity(intent)
                finish()

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        settings()
        initViews()
    }

    private fun settings() {
        postId = intent.getIntExtra(Key.INTENT_POST_ID, 0)
    }

    private fun initViews() {
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (postId != null) {
            val currentPost: Post = SampleData.postArrayList.first { it.id == postId }

            currentUserId = currentPost.user.id
            if (currentUserId == intent.getIntExtra(Key.INTENT_USER_ID,-1)){
                isSameUser = true
            }

            supportActionBar?.title = currentPost.title
            findViewById<TextView>(R.id.title_text_view).text = currentPost.title
            findViewById<TextView>(R.id.name_text_view).text = currentPost.user.name
            findViewById<TextView>(R.id.post_description_text_view).text = currentPost.description

            val profileImageView: ImageView = findViewById<ImageView>(R.id.profile_image_view)
            Glide.with(this)
                .load(currentPost.user.userImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(profileImageView)
            findViewById<TextView>(R.id.name_text_view).setOnClickListener {
                moveToUserDetailActivity()
            }
            profileImageView.setOnClickListener {
                moveToUserDetailActivity()
            }


            val postImageView : ImageView = findViewById(R.id.post_image_view)
            Glide.with(this)
                .load(currentPost.postImgUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(postImageView)
        }
    }

    private fun moveToUserDetailActivity() {
        if (postId == null) {
            Toast.makeText(this,"wrong path",Toast.LENGTH_SHORT).show()
            return
        }

        if (isSameUser) {
            finish()
            return
        }
        val intent : Intent = Intent(this,UserDetailActivity::class.java)
        intent.putExtra(Key.INTENT_USER_ID,currentUserId)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.none)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isSameUser){
            menuInflater.inflate(R.menu.toolbar_menu,menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId} ")
        when(item.itemId){
            HOME -> {
                finish()
            }
            R.id.menu_edit->{
                val intent = Intent(this,PostUpdateActivity::class.java)
                activityResultLauncher.launch(intent)
            }
            R.id.menu_delete->{

            }
        }
        return super.onOptionsItemSelected(item)
    }
}