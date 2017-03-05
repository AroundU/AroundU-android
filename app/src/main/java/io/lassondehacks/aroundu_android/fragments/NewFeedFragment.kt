package io.lassondehacks.aroundu_android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import io.lassondehacks.aroundu_android.R
import java.util.*

class NewFeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_new_feed, container, false)
        val swiperefresh = (view?.findViewById(R.id.swiperefresh) as SwipeRefreshLayout)
        (view?.findViewById(R.id.swiperefresh) as SwipeRefreshLayout).setOnRefreshListener {
            val thread = Thread {
                Thread.sleep(1000)
                activity.runOnUiThread {
                    swiperefresh.isRefreshing = false
                }
            }
            thread.start()
        }
        for (i in 0..10) {
            val ft = fragmentManager.beginTransaction()
            ft.add(
                    R.id.cards_container,
                    PostCardFragment(
                            "Post ${Random().nextInt(1000)}",
                            Date((Random().nextInt(1988694341).toLong())),
                            Random().nextInt(1000),
                            Random().nextInt(50),
                            200
                    )
            )
            ft.commit()
        }
        return view
    }
}
