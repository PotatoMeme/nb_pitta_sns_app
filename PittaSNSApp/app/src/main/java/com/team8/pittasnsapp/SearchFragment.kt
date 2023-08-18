package com.team8.pittasnsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.team8.pittasnsapp.adapter.PostRecyclerViewAdapter

private const val ARG_PARAM1 = "current_user_id"

class SearchFragment : Fragment() {
    companion object {
        fun newInstance(currentUserId: Int) = SearchFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM1, currentUserId)
            }
        }
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
            intent.putExtra("", false)
            intent.putExtra(Key.INTENT_USER_ID, currentUserId)
            intent.putExtra(Key.INTENT_POST_ID, postId)
            intent.putExtra(Key.INTENT_BEFORE_FRAGMENT, true)
            (activity as MainActivity).useActivityResultLauncher(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
        }
        view.findViewById<RecyclerView>(R.id.post_recycler_view).apply {
            adapter = postRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
        }
        postRecyclerViewAdapter.addAllPost(SampleData.postArrayList)

        val editText: EditText = view.findViewById(R.id.search_edit_text)

        view.findViewById<FloatingActionButton>(R.id.search_button).setOnClickListener {
            if (editText.text.isBlank()) {
                Toast.makeText(this@SearchFragment.context, "값을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            postRecyclerViewAdapter.changeAllPost(SampleData.postArrayList.filter {
                it.title.contains(
                    editText.text.toString()
                )
            })
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}