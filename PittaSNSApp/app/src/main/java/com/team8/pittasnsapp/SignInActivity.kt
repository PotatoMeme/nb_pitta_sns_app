package com.team8.pittasnsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()

    }

    private fun initViews() {
        val idText = findViewById<EditText>(R.id.id_edit_text)
        val pwText = findViewById<EditText>(R.id.pw_edit_text)

        findViewById<Button>(R.id.login_btn).setOnClickListener {
            val id = idText.text.toString()
            val pw = pwText.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("textFromId", id)
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.signup_btn).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}