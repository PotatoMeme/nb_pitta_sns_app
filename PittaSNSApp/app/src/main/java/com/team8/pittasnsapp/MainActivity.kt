package com.team8.pittasnsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val HOME_FRAGMENT = 100
        private const val SEARCH_FRAGMENT = 101
        private const val USER_FRAGMENT = 102
        private const val SETTING_FRAGMENT = 103
    }

    private val currentUserId: Int by lazy {
        intent.getIntExtra(Key.INTENT_LOGIN_USER_ID, 0)
    }

    private val homeFragment: HomeFragment by lazy {
        HomeFragment.newInstance(currentUserId)
    }
    private val searchFragment: SearchFragment by lazy {
        SearchFragment.newInstance(currentUserId)
    }
    private val userFragment: UserFragment by lazy {
        UserFragment.newInstance(currentUserId)
    }
    private val settingFragment: SettingFragment by lazy {
        SettingFragment.newInstance()
    }

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { actvityResult: ActivityResult ->
        when (actvityResult.resultCode) {
            Key.RESULT_OK_POST_CREATE -> {
                val title: String? = actvityResult.data?.getStringExtra(Key.INTENT_POST_TITLE)
                val description: String? =
                    actvityResult.data?.getStringExtra(Key.INTENT_POST_DESCRIPTION)

                if (title != null && description != null) {
                    SampleData.addPost(currentUserId, title, description)
                    val fragmentManager = supportFragmentManager
                    when (currentFragment) {
                        HOME_FRAGMENT -> {
                            fragmentManager.beginTransaction()
                                .detach(homeFragment).commit()
                            fragmentManager.beginTransaction()
                                .attach(homeFragment).commit()
                        }

                        SEARCH_FRAGMENT -> {
                            fragmentManager.beginTransaction()
                                .detach(searchFragment).commit()
                            fragmentManager.beginTransaction()
                                .attach(searchFragment).commit()
                        }

                        USER_FRAGMENT -> {
                            fragmentManager.beginTransaction()
                                .detach(userFragment).commit()
                            fragmentManager.beginTransaction()
                                .attach(userFragment).commit()
                        }
                    }
                }
            }

            Key.RESULT_OK_POST_DELETE -> {
                val fragmentManager = supportFragmentManager
                when (currentFragment) {
                    HOME_FRAGMENT -> {
                        fragmentManager.beginTransaction()
                            .detach(homeFragment).commit()
                        fragmentManager.beginTransaction()
                            .attach(homeFragment).commit()
                    }

                    SEARCH_FRAGMENT -> {
                        fragmentManager.beginTransaction()
                            .detach(searchFragment).commit()
                        fragmentManager.beginTransaction()
                            .attach(searchFragment).commit()
                    }

                    USER_FRAGMENT -> {
                        fragmentManager.beginTransaction()
                            .detach(userFragment).commit()
                        fragmentManager.beginTransaction()
                            .attach(userFragment).commit()
                    }
                }
            }

            Key.RESULT_OK_BEFORE_FLAGMENT -> {
                findViewById<BottomNavigationView>(R.id.bottom_navigation_view).selectedItemId = R.id.bottom_user
                val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
                toolbar.title =
                    SampleData.userArrayList.first { it.id == currentUserId }.name
                val fragmentManager = supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.frame_layout, userFragment).commit()
                fragmentManager.beginTransaction()
                    .detach(searchFragment).commit()
                fragmentManager.beginTransaction()
                    .attach(searchFragment).commit()
            }
        }
    }

    private var currentFragment: Int = HOME_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.title = "Pitta"

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frame_layout, homeFragment).commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .setOnItemSelectedListener { item ->
                hideKeyboard()
                when (item.itemId) {
                    R.id.bottom_home -> {
                        currentFragment = HOME_FRAGMENT
                        toolbar.title = "Pitta"
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment)
                            .commit()
                    }

                    R.id.bottom_search -> {
                        currentFragment = SEARCH_FRAGMENT
                        toolbar.title = "검색"
                        fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, searchFragment).commit()
                    }

                    R.id.bottom_add -> {
                        val intent = Intent(this@MainActivity, PostAddActivity::class.java)
                        activityResultLauncher.launch(intent)
                    }

                    R.id.bottom_user -> {
                        currentFragment = USER_FRAGMENT
                        toolbar.title =
                            SampleData.userArrayList.first { it.id == currentUserId }.name
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, userFragment)
                            .commit()
                    }

                    R.id.bottom_setting -> {
                        currentFragment = SETTING_FRAGMENT
                        toolbar.title = "설정"
                        fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, settingFragment).commit()
                    }
                }
                true
            }
    }

    public fun useActivityResultLauncher(intent: Intent) {
        activityResultLauncher.launch(intent)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
    }
}