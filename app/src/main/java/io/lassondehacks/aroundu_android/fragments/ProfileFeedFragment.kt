package io.lassondehacks.aroundu_android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.lassondehacks.aroundu_android.R

class ProfileFeedFragment(var ctx: Context)  : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile_feed, container, false)
    }
}
