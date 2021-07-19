package com.androidstrike.diexpa.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.androidstrike.diexpa.R
import com.androidstrike.diexpa.adapters.TabPageAdapter
import com.google.android.material.tabs.TabLayout

class Landing : Fragment() {

    lateinit var fm: FragmentManager
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tabLayout = view?.findViewById(R.id.tab_title)!!
        viewPager = view?.findViewById(R.id.view_pager)!!

        tabLayout.addTab(tabLayout.newTab().setText(R.string.daily_tab_title))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.weekly_tab_title))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = fragmentManager?.let {
            TabPageAdapter(requireContext(), it, tabLayout.tabCount)
        }
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
                tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
                tabLayout.setTabTextColors(Color.BLACK, Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
}