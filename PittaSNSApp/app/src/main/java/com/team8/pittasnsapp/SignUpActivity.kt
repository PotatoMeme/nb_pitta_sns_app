package com.team8.pittasnsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpIdEdit: TextInputLayout
    private lateinit var signUpPwEdit: TextInputLayout
    private lateinit var signUpPwCheckEdit: TextInputLayout
    private lateinit var signUpNameEdit: TextInputLayout
    private var idFlag = false
    private var pwFlag = false
    private var pwCheckFlag = false
    private var nameFlag = false
    private fun hasSpecialCharacter(string: String): Boolean {
        for (i in string.indices) {
            if (!Character.isLetterOrDigit(string[i])) {
                return true
            }
        }
        return false
    } // 특수기호 제약 함수

    private fun hasAlphabet(string: String): Boolean {
        for (i in string.indices) {
            if (Character.isAlphabetic(string[i].code)) {
                return true
            }
        } // 알파벳 제약 함수
        return false
    }

    fun idRegex(id: String): Boolean {
        if ((!hasSpecialCharacter(id)) and (hasAlphabet(id)) and (id.length >= 5)) {
            return true
        }
        return false
    }

    fun pwRegax(pw: String): Boolean {
        return pw.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
    } //비밀번호 제약 같은 경우는 손대기가 어려워서 일단 공부해온 블로그에서 긁어 왔습니다. 제약이 너무 많다 싶으면  없애셔도 됩니다.

    private val idListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
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
                flagCheck()
            }
        }
    }
    private val pwListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
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
                flagCheck()
            }
        }
    }
    private val pwCheckListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
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
                flagCheck()
            }
        }
    }
    private val nameListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        signUpNameEdit.error = "이름을 입력해주세요."
                        nameFlag = false
                    }

                    else -> {
                        signUpNameEdit.error = null
                        nameFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        initViews()
    }

    private fun initViews() {
        signUpIdEdit = findViewById(R.id.signup_id_edit_layout)
        signUpPwEdit = findViewById(R.id.signup_pw_edit_layout)
        signUpPwCheckEdit = findViewById(R.id.signup_pwcheck_edit_layout)
        signUpNameEdit = findViewById(R.id.signup_name_edit_layout)

        val signUpIdText = findViewById<EditText>(R.id.signup_id_edit)
        val signUpPwText = findViewById<EditText>(R.id.signup_pw_edit)
        val signUpPwCheckText = findViewById<EditText>(R.id.signup_pwcheck_edit)
        val signUpNameText = findViewById<EditText>(R.id.signup_name_edit)

        findViewById<Button>(R.id.signin_return).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.signup_btn).setOnClickListener {
            val signUpId = signUpIdText.text.toString()
            val signUpPw = signUpPwText.text.toString()
            val signUpPwCheck = signUpPwCheckText.text.toString()
            val signUpName = signUpNameText.text.toString()

            if (signUpId.isEmpty() || signUpPw.isEmpty() || signUpPwCheck.isEmpty() ||signUpName.isEmpty()) {
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
        signUpIdText.addTextChangedListener(idListener)
        signUpPwText.addTextChangedListener(pwListener)
        signUpPwCheckText.addTextChangedListener(pwCheckListener)
        signUpNameText.addTextChangedListener(nameListener)
    }

    private fun flagCheck() {
        // flag들의 상태에 따른 처리를 수행
    }
}
