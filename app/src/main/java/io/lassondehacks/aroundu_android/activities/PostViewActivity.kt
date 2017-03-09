package io.lassondehacks.aroundu_android.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.lassondehacks.aroundu_android.R
import kotlinx.android.synthetic.main.activity_post_view.*

class PostViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        Picasso.with(this).load(url).into(findViewById(R.id.imageView) as ImageView)

        textView.text = title
    }
}
