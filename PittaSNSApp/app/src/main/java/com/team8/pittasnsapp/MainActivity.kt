package com.team8.pittasnsapp

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment: HomeFragment by lazy {
        HomeFragment.newInstance()
    }
    private val searchFragment: SearchFragment by lazy {
        SearchFragment.newInstance()
    }
    private val userFragment: UserFragment by lazy {
        UserFragment.newInstance()
    }
    private val settingFragment: SettingFragment by lazy {
        SettingFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        findViewById<Toolbar>(R.id.main_toolbar).title = "Pitta"

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.frame_layout, homeFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frame_layout, searchFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frame_layout, userFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frame_layout, settingFragment).commit()

        fragmentManager.beginTransaction().show(homeFragment).commit()
        fragmentManager.beginTransaction().hide(searchFragment).commit()
        fragmentManager.beginTransaction().hide(settingFragment).commit()
        fragmentManager.beginTransaction().hide(userFragment).commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .setOnItemSelectedListener { item ->
                hideKeyboard()
                when (item.itemId) {
                    R.id.bottom_home -> {
                        fragmentManager.beginTransaction().hide(searchFragment).commit()
                        fragmentManager.beginTransaction().hide(userFragment).commit()
                        fragmentManager.beginTransaction().hide(settingFragment).commit()
                        fragmentManager.beginTransaction().show(homeFragment).commit()
                    }

                    R.id.bottom_search -> {
                        fragmentManager.beginTransaction().hide(homeFragment).commit()
                        fragmentManager.beginTransaction().hide(userFragment).commit()
                        fragmentManager.beginTransaction().hide(settingFragment).commit()
                        fragmentManager.beginTransaction().show(searchFragment).commit()
                    }

                    R.id.bottom_user -> {
                        fragmentManager.beginTransaction().hide(homeFragment).commit()
                        fragmentManager.beginTransaction().hide(searchFragment).commit()
                        fragmentManager.beginTransaction().hide(settingFragment).commit()
                        fragmentManager.beginTransaction().show(userFragment).commit()
                    }

                    R.id.bottom_setting -> {
                        fragmentManager.beginTransaction().hide(homeFragment).commit()
                        fragmentManager.beginTransaction().hide(searchFragment).commit()
                        fragmentManager.beginTransaction().hide(userFragment).commit()
                        fragmentManager.beginTransaction().show(settingFragment).commit()
                    }
                }
                true
            }
    }

    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
    }
}