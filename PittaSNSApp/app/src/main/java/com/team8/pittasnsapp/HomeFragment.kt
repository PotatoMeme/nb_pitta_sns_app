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


private const val ARG_PARAM1 = "current_user_id"

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(currentUserId: Int) = HomeFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, currentUserId)
            }
        }

        private const val TAG = "HomeFragment"
        //private var count1 = 0
        //private var count2 = 0
    }

    private var currentUserId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentUserId = it.getInt(ARG_PARAM1)
        }
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
                intent.putExtra(Key.INTENT_USER_ID, userId)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
            }.apply {
                addAllUser(SampleData.userArrayList.filter { it.id != currentUserId })
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
                intent.putExtra(Key.INTENT_USER_ID, currentUserId)
                intent.putExtra(Key.INTENT_POST_ID, postId)
                startActivity(intent)
                activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
            }.apply {
                addAllPost(SampleData.postArrayList)
            }

            layoutManager = LinearLayoutManager(this@HomeFragment.context)
        }
    }
}