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
        val joinText: String = getString(R.string.signup_title)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = joinText
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
            val joinToastBlank: String = getString(R.string.join_btn_toast_fail_blank)
            val joinToastCondition: String = getString(R.string.join_btn_toast_fail_condition)
            val joinToastSuccess: String = getString(R.string.join_btn_toast_success)

            if (signUpId.isEmpty() || signUpPw.isEmpty() || signUpPwCheck.isEmpty() || signUpName.isEmpty()) {
                Toast.makeText(this, joinToastBlank, Toast.LENGTH_SHORT).show()
            } else if (flagCheck()) {
                val intent = Intent()
                intent.putExtra("signedUpId", signUpId)
                intent.putExtra("signedUpPw", signUpPw)
                SampleData.addUser(signUpName, signUpId, signUpPw)
                setResult(Activity.RESULT_OK, intent)
                Toast.makeText(this, joinToastSuccess, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, joinToastCondition, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(R.id.id_check_button).setOnClickListener {
            val checkToastCondition: String = getString(R.string.check_btn_toast_condition)
            val checkToastDuplicate: String = getString(R.string.check_btn_toast_duplicate)
            val checkToastSuccess: String = getString(R.string.check_btn_toast_success)
            if (!idFlag) {
                signUpIdEdit.error = checkToastCondition
                idDucplicatedCheckFlag = false
            } else if (SampleData.userArrayList.any { it.personalId == signUpIdText.text.toString() }) {
                signUpIdEdit.error = checkToastDuplicate
                idDucplicatedCheckFlag = false
            } else {
                signUpIdEdit.error = null
                signUpIdEdit.helperText = checkToastSuccess
                idDucplicatedCheckFlag = true
            }
        }

        signUpIdText.addTextChangedListener { s: Editable? ->
            val idToastBlank: String = getString(R.string.signup_id_blank)
            val idToastConditon: String = getString(R.string.signup_id_condition)
            if (s != null) {
                idDucplicatedCheckFlag = false
                when {
                    s.isEmpty() -> {
                        signUpIdEdit.error = idToastBlank
                        idFlag = false
                    }

                    !idRegex(s.toString()) -> {
                        signUpIdEdit.error = idToastConditon
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
            val pwToastBlank: String = getString(R.string.signup_pw_blank)
            val pwToastConditon: String = getString(R.string.signup_pw_condition)
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        signUpPwEdit.error = pwToastBlank
                        pwFlag = false
                    }

                    !pwRegax(s.toString()) -> {
                        signUpPwEdit.error = pwToastConditon
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
            val pwCheckToastBlank: String = getString(R.string.signup_pw_check_blank)
            val pwCheckToastConditon: String = getString(R.string.signup_pw_check_conditon)

            if (s != null) {
                val pw = signUpPwEdit.editText?.text.toString()
                when {
                    s.isEmpty() -> {
                        signUpPwCheckEdit.error = pwCheckToastBlank
                        pwCheckFlag = false
                    }

                    pw != (s.toString()) -> {
                        signUpPwCheckEdit.error = pwCheckToastConditon
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
            val nameToastBlank: String = getString(R.string.signup_name_blank)
            if (s != null) {
                if (s.isBlank()) {
                    signUpNameEdit.error = nameToastBlank
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
