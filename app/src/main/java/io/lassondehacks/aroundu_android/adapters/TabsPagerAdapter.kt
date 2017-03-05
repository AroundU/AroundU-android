package io.lassondehacks.aroundu_android.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.lassondehacks.aroundu_android.fragments.DistanceFeedFragment
import io.lassondehacks.aroundu_android.fragments.HotFeedFragment
import io.lassondehacks.aroundu_android.fragments.NewFeedFragment

class TabsPagerAdapter(fm: FragmentManager, tabCount: Int) : FragmentStatePagerAdapter(fm) {

    var tabCount: Int = 0

    init {
        this.tabCount = tabCount
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return HotFeedFragment()
            1 -> return NewFeedFragment()
            else -> return DistanceFeedFragment()
        }
    }

    override fun getCount(): Int {
        return this.tabCount
    }
}