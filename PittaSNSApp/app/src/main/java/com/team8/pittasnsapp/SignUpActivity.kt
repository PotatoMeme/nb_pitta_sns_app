package com.team8.pittasnsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.team8.pittasnsapp.Util.idRegex
import com.team8.pittasnsapp.Util.pwRegax

class SignUpActivity : AppCompatActivity() {
    companion object {
        private const val HOME = 16908332
    }

    private lateinit var signUpIdEdit: TextInputLayout
    private lateinit var signUpPwEdit: TextInputLayout
    private lateinit var signUpPwCheckEdit: TextInputLayout
    private lateinit var signUpNameEdit: TextInputLayout
    private var idFlag = false
    private var idDucplicatedCheckFlag = false
    private var pwFlag = false
    private var pwCheckFlag = false
    private var nameFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()
    }

    private fun initViews() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "회원가입"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        signUpIdEdit = findViewById(R.id.signup_id_edit_layout)
        signUpPwEdit = findViewById(R.id.signup_pw_edit_layout)
        signUpPwCheckEdit = findViewById(R.id.signup_pwcheck_edit_layout)
        signUpNameEdit = findViewById(R.id.signup_name_edit_layout)

        val signUpIdText = findViewById<EditText>(R.id.signup_id_edit)
        val signUpPwText = findViewById<EditText>(R.id.signup_pw_edit)
        val signUpPwCheckText = findViewById<EditText>(R.id.signup_pwcheck_edit)
        val signUpNameText = findViewById<EditText>(R.id.signup_name_edit)

        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.submit_button).setOnClickListener {
            val signUpId = signUpIdText.text.toString()
            val signUpPw = signUpPwText.text.toString()
            val signUpPwCheck = signUpPwCheckText.text.toString()
            val signUpName = signUpNameText.text.toString()

            if (signUpId.isEmpty() || signUpPw.isEmpty() || signUpPwCheck.isEmpty() || signUpName.isEmpty()) {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show()
            } else if (flagCheck()) {
                val intent = Intent()
                intent.putExtra("signedUpId", signUpId)
                intent.putExtra("signedUpPw", signUpPw)
                SampleData.addUser(signUpName, signUpId, signUpPw)
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "모든조건을 수행해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.id_check_button).setOnClickListener {
            if (!idFlag) {
                signUpIdEdit.error = "아이디 조건을 먼저 만족해주세요"
                idDucplicatedCheckFlag = false
            } else if (SampleData.userArrayList.any { it.personalId == signUpIdText.text.toString() }) {
                signUpIdEdit.error = "중복되는 계정이 존재합니다. 다른 계정을 생성해주세요"
                idDucplicatedCheckFlag = false
            } else {
                signUpIdEdit.error = null
                signUpIdEdit.helperText = "중복되는 계정이 없습니다."
                idDucplicatedCheckFlag = true
            }
        }

        signUpIdText.addTextChangedListener { s: Editable? ->
            if (s != null) {
                idDucplicatedCheckFlag = false
                when {
                    s.isEmpty() -> {
                        signUpIdEdit.error = "아이디를 입력해주세요."
                        idFlag = false
                    }

                    !idRegex(s.toString()) -> {
                        signUpIdEdit.error = "아이디 양식이 맞지 않습니다"
                        idFlag = false
                    }

                    else -> {
                        signUpIdEdit.error = null
                        idFlag = true
                    }
                }
            }
        }
        signUpPwText.addTextChangedListener { s: Editable? ->
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        signUpPwEdit.error = "비밀번호를 입력해주세요."
                        pwFlag = false
                    }

                    !pwRegax(s.toString()) -> {
                        signUpPwEdit.error = "비밀번호 양식이 일치하지 않습니다."
                        pwFlag = false
                    }

                    else -> {
                        signUpPwEdit.error = null
                        pwFlag = true
                    }
                }
            }
        }
        signUpPwCheckText.addTextChangedListener { s: Editable? ->
            if (s != null) {
                val pw = signUpPwEdit.editText?.text.toString()
                when {
                    s.isEmpty() -> {
                        signUpPwCheckEdit.error = "비밀번호를 입력해주세요."
                        pwCheckFlag = false
                    }

                    pw != (s.toString()) -> {
                        signUpPwCheckEdit.error = "비밀번호가 일치하지 않습니다."
                        pwCheckFlag = false
                    }

                    else -> {
                        signUpPwCheckEdit.error = null
                        pwCheckFlag = true
                    }
                }
            }
        }
        signUpNameText.addTextChangedListener { s: Editable? ->
            if (s != null) {
                if (s.isBlank()) {
                    signUpNameEdit.error = "이름을 입력해주세요."
                    nameFlag = false
                } else {
                    signUpNameEdit.error = null
                    nameFlag = true
                }
            }
        }
    }


    private fun flagCheck(): Boolean {
        return idFlag && idDucplicatedCheckFlag && pwFlag && pwCheckFlag && nameFlag
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            HOME -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
