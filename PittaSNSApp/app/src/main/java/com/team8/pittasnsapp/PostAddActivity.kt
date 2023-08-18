package com.team8.pittasnsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class PostAddActivity : AppCompatActivity() {
    companion object {
        private const val HOME = 16908332
        private const val TAG = "PostAddActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add)

        initViews()
    }

    private fun initViews() {
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.pitta)

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            val titleEditText: EditText = findViewById(R.id.title_edite_text)
            val descriptionEditText: EditText = findViewById(R.id.descriptions_edite_text)

            if (titleEditText.text.isBlank() || descriptionEditText.text.isBlank()) {
                Toast.makeText(this, getString(R.string.check_value_question), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            intent.putExtra(Key.INTENT_POST_TITLE, titleEditText.text.toString())
            intent.putExtra(Key.INTENT_POST_DESCRIPTION, descriptionEditText.text.toString())
            setResult(Key.RESULT_OK_POST_CREATE, intent)
            finish()
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