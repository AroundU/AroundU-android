package io.lassondehacks.aroundu_android.services

import android.content.Context
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.lassondehacks.aroundu_android.domain.User
import java.util.*

class AuthenticationService {
    companion object {
        val API_URL: String = "http://lassondehacks.io:8089"

        fun login(username: String, password: String, cb: (err: String?, result: User?) -> Unit) {
            var body: List<Pair<String, Any?>> = listOf(
                    Pair("username", username),
                    Pair("password", password))
            (API_URL + "/auth/login").httpPost(body).responseJson { request, response, result ->
                result.fold({ d ->
                    val jsonObj = result.component1()?.obj()
                    if(jsonObj?.getBoolean("success") == true) {
                        var cookie = response.httpResponseHeaders["set-cookie"]?.first()
                        val user = jsonObj?.getJSONObject("user")
                        val username = user?.getString("username")
//                        val emailAddress = user?.getString("email")
                        cb(null, User(username, "", cookie))
                    } else {
                        cb("Error", null)
                    }
                }, { err ->
                    cb(err.message, null)
                })
            }

//            val thread = Thread {
//                Thread.sleep(1000)
//                cb(null, User("test", "test@test.com"))
//            }
//            thread.start()
        }

        fun register(username: String, email: String, password: String, cb: (err: String, result: User) -> Unit) {
            var body: List<Pair<String, Any?>> = listOf(
                    Pair("username", username),
                    Pair("email", email),
                    Pair("password", password))
            (API_URL + "/register").httpPost(body).responseJson { request, response, result ->
                val jsonObj = result.component1()?.obj()
//                val username = jsonObj?.getString("username")
//                val emailAddress = jsonObj?.getString("email")
            }
        }

        fun isLoggedIn(context: Context): Boolean {
            val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            return preferences.getString("saved_user_cookie", null) != null
        }
    }
}