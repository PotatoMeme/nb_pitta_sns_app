package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.adapter.PostRecyclerView
import com.team8.pittasnsapp.adapter.ProfileRecyclerView
import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User

class UserFragment : Fragment() {
    companion object {
        fun newInstance() = UserFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {


    }
}