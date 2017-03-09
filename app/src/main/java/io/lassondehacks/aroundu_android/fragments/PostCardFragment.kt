package io.lassondehacks.aroundu_android.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.activities.PostViewActivity
import io.lassondehacks.aroundu_android.domain.Post
import org.ocpsoft.prettytime.PrettyTime
import java.security.Timestamp
import java.util.*

class PostCardFragment(val post: Post,
                       val ctx: Context,
                       val title: String,
                       val timestamp: Date,
                       val distance: Int,
                       val repliesCount: Int,
                       val score: Int,
                       val thumbnail: String,
                       val upvoted: Boolean) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_post_card, container, false)
        (view?.findViewById(R.id.post_title) as TextView).text = title

        Picasso.with(ctx).load(thumbnail).into(view?.findViewById(R.id.imageView) as ImageView)

        (view?.findViewById(R.id.card_view) as CardView).setOnClickListener {
            val intent = Intent(ctx, PostViewActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", post.media.url)
            startActivity(intent)
        }

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

        if (upvoted) {
            (view?.findViewById(R.id.upvoted_bar) as FrameLayout).visibility = 0
        } else {
            (view?.findViewById(R.id.upvoted_bar) as FrameLayout).visibility = 4
        }

        return view
    }
}
