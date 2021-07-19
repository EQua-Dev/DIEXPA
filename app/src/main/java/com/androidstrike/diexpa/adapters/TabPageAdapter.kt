package com.androidstrike.diexpa.adapters

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.androidstrike.diexpa.ui.Daily
import com.androidstrike.diexpa.ui.Landing
import com.androidstrike.diexpa.ui.Weekly

class TabPageAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when(position){
            0 ->{
                Daily()
            }
            1 ->{
                Weekly()
            }else -> getItem(position)
        }
    }
}