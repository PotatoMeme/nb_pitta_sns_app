package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.team8.pittasnsapp.adapter.PostRecyclerViewAdapter


class SearchFragment : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val postRecyclerViewAdapter: PostRecyclerViewAdapter = PostRecyclerViewAdapter { postId ->
            val intent: Intent =
                Intent(this@SearchFragment.context, PostDetailActivity::class.java)
            intent.putExtra(Key.INTENT_POST_ID, postId)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
        }
        view.findViewById<RecyclerView>(R.id.post_recycler_view).apply {
            adapter = postRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
        }
        postRecyclerViewAdapter.addAllPost(SampleData.postArrayList)

        view.findViewById<FloatingActionButton>(R.id.search_button).setOnClickListener {
            val editText: EditText = view.findViewById(R.id.search_edit_text)
            if (editText.text.isBlank()) {
                Toast.makeText(this@SearchFragment.context, "값을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            postRecyclerViewAdapter.changeAllPost(SampleData.postArrayList.filter {
                it.title.contains(
                    editText.text.toString()
                )
            })

        }
    }
}