package io.lassondehacks.aroundu_android.activities

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.lassondehacks.aroundu_android.R
import io.lassondehacks.aroundu_android.services.PostService
import kotlinx.android.synthetic.main.activity_post_edit_view.*


class ImagePostEditViewActivity : AppCompatActivity(){

    var imagePath: String = ""

    var latitude: Double = 0.0
    var longitude: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_edit_view)


        imagePath = intent.getStringExtra("image_path")
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        setPostEnabled()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }


    fun setPostEnabled() {
        var image = BitmapFactory.decodeFile(imagePath)
        imageView2.setImageBitmap(image)
        imageView2.rotation = 90.0f
        btn_submit.setOnClickListener {
            val thread = Thread {
                val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                PostService.makeFilePost(image, (input_title).text.toString(), latitude.toFloat(), longitude.toFloat(), preferences.getString("saved_user_cookie", ""),
                        { req, res ->
                            println("")
                        })
            }
            thread.start()
            finish()
        }
    }

}
