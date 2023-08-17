package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.team8.pittasnsapp.adapter.PostRecyclerViewAdapter
import com.team8.pittasnsapp.adapter.ProfileRecyclerViewAdapter


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()

        private const val TAG = "HomeFragment"
        //private var count1 = 0
        //private var count2 = 0
    }
    init {
        //Log.d(TAG, "init: ${count1++}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        //Log.d(TAG, "initView: ${count2++}")
        view.findViewById<RecyclerView>(R.id.profile_recycler_view).apply {
            adapter = ProfileRecyclerViewAdapter { userId ->
                val intent: Intent =
                    Intent(this@HomeFragment.context, UserDetailActivity::class.java)
                intent.putExtra(Key.INTENT_USER_ID,userId)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.none)
            }.apply {
                addAllUser(SampleData.userArrayList)
            }
            layoutManager =
                LinearLayoutManager(
                    this@HomeFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }

        view.findViewById<RecyclerView>(R.id.post_recycler_view).apply {
            adapter = PostRecyclerViewAdapter { postId ->
                val intent: Intent =
                    Intent(this@HomeFragment.context, PostDetailActivity::class.java)
                intent.putExtra(Key.INTENT_POST_ID,postId)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right,R.anim.none)
            }.apply {
                addAllPost(SampleData.postArrayList)
            }

            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }
}