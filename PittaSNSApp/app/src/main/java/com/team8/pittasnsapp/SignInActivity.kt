package com.team8.pittasnsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar

class SignInActivity : AppCompatActivity() {
    private lateinit var resultIdPw: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()
    }

    private fun initViews() {
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pitta"

        val idText = findViewById<EditText>(R.id.id_edit_text)
        val pwText = findViewById<EditText>(R.id.pw_edit_text)

        resultIdPw = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val signedUpId = data?.getStringExtra("signedUpId")
                val signedUpPw = data?.getStringExtra("signedUpPw")
                // Use the data received from SignUpActivity (if needed)
                idText.setText(signedUpId)
                pwText.setText(signedUpPw)
            }
        }

        findViewById<Button>(R.id.login_btn).setOnClickListener {
            val toastFail:String = getString(R.string.signin_toast_fail)
            val toastSuccess:String = getString(R.string.signin_toast_success)
            val id = idText.text.toString()
            val pw = pwText.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            if (id.isEmpty() || pw.isEmpty() || !SampleData.userArrayList.any{it.personalId == id && it.personalPassword == pw}) {
                Toast.makeText(this,toastFail, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, toastSuccess, Toast.LENGTH_SHORT).show()
                intent.putExtra(Key.INTENT_LOGIN_USER_ID,SampleData.userArrayList.first{it.personalId == id && it.personalPassword == pw}.id )
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.none)
                finish()
            }
        }

        findViewById<Button>(R.id.signup_btn).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultIdPw.launch(intent)
        }
    }
}