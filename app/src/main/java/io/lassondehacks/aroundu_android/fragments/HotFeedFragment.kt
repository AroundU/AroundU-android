package io.lassondehacks.aroundu_android.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.domain.Post
import io.lassondehacks.aroundu_android.services.FeedService
import io.lassondehacks.aroundu_android.services.LocationService

class HotFeedFragment(var ctx: Context) : Fragment() {

    var postObjects: List<Post> = arrayListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_hot_feed, container, false)
        val swiperefresh = (view?.findViewById(R.id.swiperefresh) as SwipeRefreshLayout)
        (view?.findViewById(R.id.swiperefresh) as SwipeRefreshLayout).setOnRefreshListener {
            val thread = Thread {
                refreshFeed()
                activity.runOnUiThread {
                    swiperefresh.isRefreshing = false
                }
            }
            thread.start()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(postObjects.size == 0){
            refreshFeed()
        } else {
            populatePostCards()
        }
    }

    fun refreshFeed() {
        val preferences = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        FeedService.getHotFeedPosts(
                LocationService.lastLocation?.latitude,
                LocationService.lastLocation?.longitude,
                preferences.getString("saved_user_cookie", ""),
                { err, posts ->
                    if (err == null) {
                        this.postObjects = posts
                        populatePostCards()
                    }
                }
        )
    }

    fun populatePostCards() {
        (view?.findViewById(R.id.cards_container) as LinearLayout).removeAllViews()
        val ft = fragmentManager.beginTransaction()
        for (post in postObjects) {
            var currentLatitude: Float = LocationService.lastLocation?.latitude?.toFloat() ?: 0.0f
            var currentLongitude: Float = LocationService.lastLocation?.longitude?.toFloat() ?: 0.0f
            var distance = distanceBetweenPoints(currentLatitude, currentLongitude, post.latitude, post.longitude)
            ft.add(
                    R.id.cards_container,
                    PostCardFragment(
                            post,
                            ctx,
                            post.description,
                            post.timestamp,
                            distance.toInt(),
                            post.comments.size,
                            post.upvotes - post.downvotes,
                            post.media.thumbnail,
                            post.upvoted
                    )
            )
        }
        ft.commit()
    }

    fun distanceBetweenPoints(latitude1: Float, longitude1: Float, latitude2: Float, longitude2: Float): Double {
        val d2r = (Math.PI / 180.0)
        val dlong = (longitude2 - longitude1) * d2r
        val dlat = (latitude2 - latitude1) * d2r
        val a = Math.pow(Math.sin(dlat / 2.0), 2.0) + Math.cos(latitude1 * d2r) * Math.cos(latitude2 * d2r) * Math.pow(Math.sin(dlong / 2.0), 2.0)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val d = 6367 * c

        return d
    }
}
