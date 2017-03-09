package io.lassondehacks.aroundu_android.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import io.lassondehacks.aroundu_android.fragments.DistanceFeedFragment
import io.lassondehacks.aroundu_android.fragments.HotFeedFragment
import io.lassondehacks.aroundu_android.fragments.NewFeedFragment
import io.lassondehacks.aroundu_android.fragments.ProfileFeedFragment

class TabsPagerAdapter(ctx: Context, fm: FragmentManager, tabCount: Int) : FragmentStatePagerAdapter(fm) {
    var hotFeed = HotFeedFragment(ctx)
    var newFeed = NewFeedFragment(ctx)
    var distanceFeed = DistanceFeedFragment(ctx)
    var profileFeed = ProfileFeedFragment(ctx)

    var tabCount: Int = 0

    init {
        this.tabCount = tabCount
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return hotFeed
            1 -> return newFeed
            2 -> return distanceFeed
            else -> return profileFeed
        }
    }

    override fun getCount(): Int {
        return this.tabCount
    }
}