package com.team8.pittasnsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        initViews()
    }

    private fun initViews() {
        val signUpIdText = findViewById<EditText>(R.id.signup_id_edit)
        val signUpPwText = findViewById<EditText>(R.id.signup_pw_edit)
        val signUpNameText = findViewById<EditText>(R.id.signup_name_edit)

        findViewById<Button>(R.id.signin_return).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.signup_btn).setOnClickListener {
            val signUpId = signUpIdText.text.toString()
            val signUpPw = signUpPwText.text.toString()
            val signUpName = signUpNameText.text.toString()

            if (signUpId.isEmpty() || signUpPw.isEmpty() || signUpName.isEmpty()) {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra("signedUpId", signUpId)
                intent.putExtra("signedUpPw", signUpPw)
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}