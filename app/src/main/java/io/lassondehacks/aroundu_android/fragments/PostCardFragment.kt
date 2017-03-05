package io.lassondehacks.aroundu_android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.domain.Post
import org.ocpsoft.prettytime.PrettyTime
import java.security.Timestamp
import java.util.*

class PostCardFragment(val title: String, val timestamp: Date, val distance: Int, val repliesCount: Int, val score: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_post_card, container, false)
        (view?.findViewById(R.id.post_title) as TextView).text = title
        var pt = PrettyTime()
        var timeAgo = pt.format(timestamp)
        var distanceAway = "${distance}m away"
        (view?.findViewById(R.id.post_info_1) as TextView).text = "Posted $timeAgo. $distanceAway"
        (view?.findViewById(R.id.post_info_2) as TextView).text = "$repliesCount replies"
        if (score >= 0) {
            (view?.findViewById(R.id.score) as TextView).setTextColor(R.color.holo_green_dark)

            (view?.findViewById(R.id.score) as TextView).text = "+$score"
        } else {
            (view?.findViewById(R.id.score) as TextView).setTextColor(R.color.holo_red_dark)

            (view?.findViewById(R.id.score) as TextView).text = "-$score"
        }
        return view
    }
}
