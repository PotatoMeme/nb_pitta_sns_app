package com.team8.pittasnsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.team8.pittasnsapp.adapter.PostRecyclerView
import com.team8.pittasnsapp.adapter.ProfileRecyclerView
import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User

class MainActivity : AppCompatActivity() {

    private val homeFragment : HomeFragment by lazy {
        HomeFragment.newInstance()
    }
    private val userFragment : UserFragment by lazy {
        UserFragment.newInstance()
    }
    private val settingFragment : SettingFragment by lazy {
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
        fragmentManager.beginTransaction().add(R.id.frame_layout,homeFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frame_layout,userFragment).commit()
        fragmentManager.beginTransaction().add(R.id.frame_layout,settingFragment).commit()

        fragmentManager.beginTransaction().show(homeFragment).commit()
        fragmentManager.beginTransaction().hide(settingFragment).commit()
        fragmentManager.beginTransaction().hide(userFragment).commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .setOnItemSelectedListener{ item ->
                when(item.itemId){
                    R.id.bottom_home -> {
                        fragmentManager.beginTransaction().hide(userFragment).commit()
                        fragmentManager.beginTransaction().hide(settingFragment).commit()
                        fragmentManager.beginTransaction().show(homeFragment).commit()
                    }
                    R.id.bottom_user -> {
                        fragmentManager.beginTransaction().hide(homeFragment).commit()
                        fragmentManager.beginTransaction().hide(settingFragment).commit()
                        fragmentManager.beginTransaction().show(userFragment).commit()
                    }
                    R.id.bottom_setting -> {
                        fragmentManager.beginTransaction().hide(homeFragment).commit()
                        fragmentManager.beginTransaction().hide(userFragment).commit()
                        fragmentManager.beginTransaction().show(settingFragment).commit()
                    }
                }
                true
            }
    }
}