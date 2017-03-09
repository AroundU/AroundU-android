package io.lassondehacks.aroundu_android.services

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import io.lassondehacks.aroundu_android.domain.Media
import io.lassondehacks.aroundu_android.domain.Post
import io.lassondehacks.aroundu_android.domain.User
import org.json.JSONArray
import java.util.*

class FeedService {
    companion object {

        val API_URL: String = "http://lassondehacks.io:8089"

        fun getNewFeedPosts(latitude: Double?, longitude: Double?, cookie: String, cb: (err: String?, posts: List<Post>) -> Unit) {
            FuelManager.instance.baseHeaders = mapOf("Cookie" to cookie)
            (API_URL + "/post/new/" + longitude + "/" + latitude + "/0/30")
                    .httpGet()
                    .responseJson { request, response, result ->
                        result.fold({ d ->
                            val jsonObj = result.component1()?.obj()
                            if (jsonObj?.getBoolean("success") == true) {
                                var cookie = response.httpResponseHeaders["set-cookie"]?.first()
                                val posts = jsonObj?.getJSONArray("posts")
                                val postsList = getPostsfromJSONArray(posts)
                                cb(null, postsList)
                            } else {
                                cb("Error", arrayListOf())
                            }
                        }, { err ->
                            cb(err.message, arrayListOf())
                        })
                    }
        }
        fun getHotFeedPosts(latitude: Double?, longitude: Double?, cookie: String, cb: (err: String?, posts: List<Post>) -> Unit) {
            FuelManager.instance.baseHeaders = mapOf("Cookie" to cookie)
            (API_URL + "/post/hot/" + longitude + "/" + latitude + "/0/30")
                    .httpGet()
                    .responseJson { request, response, result ->
                        result.fold({ d ->
                            val jsonObj = result.component1()?.obj()
                            if (jsonObj?.getBoolean("success") == true) {
                                var cookie = response.httpResponseHeaders["set-cookie"]?.first()
                                val posts = jsonObj?.getJSONArray("posts")
                                val postsList = getPostsfromJSONArray(posts)
                                cb(null, postsList)
                            } else {
                                cb("Error", arrayListOf())
                            }
                        }, { err ->
                            cb(err.message, arrayListOf())
                        })
                    }
        }

        fun getDistanceFeedPosts(latitude: Double?, longitude: Double?, cookie: String, cb: (err: String?, posts: List<Post>) -> Unit) {
            FuelManager.instance.baseHeaders = mapOf("Cookie" to cookie)
            (API_URL + "/post/near/" + longitude + "/" + latitude + "/0/30")
                    .httpGet()
                    .responseJson { request, response, result ->
                        result.fold({ d ->
                            val jsonObj = result.component1()?.obj()
                            if (jsonObj?.getBoolean("success") == true) {
                                var cookie = response.httpResponseHeaders["set-cookie"]?.first()
                                val posts = jsonObj?.getJSONArray("posts")
                                val postsList = getPostsfromJSONArray(posts)
                                cb(null, postsList)
                            } else {
                                cb("Error", arrayListOf())
                            }
                        }, { err ->
                            cb(err.message, arrayListOf())
                        })
                    }
        }

        fun getPostsfromJSONArray(posts: JSONArray): MutableList<Post> {
            var postsArr: MutableList<Post> = mutableListOf()
            for (i in 0..posts.length() - 1) {
                var postObjLarge = posts.getJSONObject(i)
                var postObj = postObjLarge.getJSONObject("post")
                postsArr.add(
                        Post(
                                postObj.getString("_id"),
                                User(
                                        postObj.getJSONObject("user").getString("username"),
                                        null,
                                        null
                                ),
                                null,
                                Media(
                                        postObj.getJSONObject("media").getString("_id"),
                                        postObj.getJSONObject("media").getString("mimetype"),
                                        postObj.getJSONObject("media").getString("name"),
                                        postObj.getJSONObject("media").getDouble("size").toFloat(),
                                        postObj.getJSONObject("media").getString("url"),
                                        postObj.getJSONObject("media").getString("thumbnail")
                                ),
                                postObj.getString("description"),
                                postObj.getJSONObject("position").getJSONArray("coordinates").getDouble(1).toFloat(),
                                postObj.getJSONObject("position").getJSONArray("coordinates").getDouble(0).toFloat(),
                                Date(postObj.getLong("timestamp")),
                                postObj.getInt("upvotes"),
                                postObj.getInt("downvotes"),
                                arrayListOf<Post>(),
                                postObjLarge.getBoolean("upvoted")
                        )
                )
            }
            return postsArr
        }
    }
}