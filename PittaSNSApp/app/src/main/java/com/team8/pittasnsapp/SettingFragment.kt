package com.team8.pittasnsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

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
                "로그아웃",
                "내 정보",
                "폰트",
                "테마",
            )
        val arrayAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                settingMenuTitleArray
            )
        }
        listView.adapter = arrayAdapter
    }
}