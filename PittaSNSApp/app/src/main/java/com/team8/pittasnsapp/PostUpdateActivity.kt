package com.team8.pittasnsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.team8.pittasnsapp.model.Post

class PostUpdateActivity : AppCompatActivity() {
    companion object {
        private const val HOME = 16908332
        private const val TAG = "PostUodatedActivity"
    }

    private var postId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_update)

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
        if (postId != null){
            val currentPost: Post = SampleData.postArrayList.first { it.id == postId }
            supportActionBar?.title = "Pitta"
            val titleEditText: EditText = findViewById(R.id.title_edite_text)
            val descriptionEditText: EditText = findViewById(R.id.descriptions_edite_text)
            titleEditText.setText(currentPost.title)
            descriptionEditText.setText(currentPost.description)

            findViewById<Button>(R.id.submit_button).setOnClickListener {

                if (titleEditText.text.isBlank() || descriptionEditText.text.isBlank()) {
                    Toast.makeText(this, "값을 확인해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                intent.putExtra(Key.INTENT_POST_TITLE, titleEditText.text.toString())
                intent.putExtra(Key.INTENT_POST_DESCRIPTION, descriptionEditText.text.toString())
                setResult(Key.RESULT_OK_POST_UPDATE, intent)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId} ")
        when (item.itemId) {
            HOME -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}