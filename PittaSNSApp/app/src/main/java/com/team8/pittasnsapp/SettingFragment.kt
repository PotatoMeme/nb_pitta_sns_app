package com.team8.pittasnsapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.NonDisposableHandle.parent

class SettingFragment : Fragment() {
    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        val listView: ListView = view.findViewById<ListView>(R.id.list_view)
        val settingMenuTitleArray: Array<String> =
            arrayOf(
                getString(R.string.user_detail_logout),
                getString(R.string.mydata_text),
            )
        val arrayAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                settingMenuTitleArray
            )
        }
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                val sb = Snackbar.make(
                    view,
                    getString(R.string.logout_question),
                    Snackbar.LENGTH_LONG
                )
                sb.setAction(getString(R.string.submit_text)) {
                    val intent = Intent(activity, SignInActivity::class.java)
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
                    activity?.finish()
                }
                sb.show()
            }


        }
    }
}