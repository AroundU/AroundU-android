package io.lassondehacks.aroundu_android.services

import android.graphics.Bitmap
import com.github.kittinunf.fuel.core.interceptors.redirectResponseInterceptor
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.squareup.okhttp.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class PostService {
    companion object {

        val API_URL: String = "http://lassondehacks.io:8089"
        fun makeFilePost(file: Bitmap, title: String, latitude: Float?, longitude: Float?, cookie: String, cb: (req: Request?, res: Response?) -> Unit) {
            val stream = ByteArrayOutputStream()
            file.compress(Bitmap.CompressFormat.PNG, 20, stream)
            var client = OkHttpClient()
            client.setConnectTimeout(60, TimeUnit.SECONDS)
            client.setReadTimeout(60, TimeUnit.SECONDS)
            client.setWriteTimeout(60, TimeUnit.SECONDS)
            var requestBody = MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("file", "filename",
                            RequestBody.create(MediaType.parse("image/png"), stream.toByteArray()))
                    .addFormDataPart("description", title)
                    .addFormDataPart("latitude", latitude.toString())
                    .addFormDataPart("longitude", longitude.toString())
                    .build()

            var request = Request.Builder()
                    .url(API_URL + "/post")
                    .addHeader("Cookie", cookie)
                    .post(requestBody)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(p0: Response?) {
                    cb(null, p0)
                }
                override fun onFailure(p0: Request?, p1: IOException?) {
                    cb(p0, null)
                }

            })
        }

        fun makeSimplePost(title: String, latitude: Float?, longitude: Float?, cookie: String, cb: (req: Request?, res: Response?) -> Unit) {
            var client = OkHttpClient()
            client.setConnectTimeout(60, TimeUnit.SECONDS)
            client.setReadTimeout(60, TimeUnit.SECONDS)
            client.setWriteTimeout(60, TimeUnit.SECONDS)
            var requestBody = MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("description", title)
                    .addFormDataPart("latitude", latitude.toString())
                    .addFormDataPart("longitude", longitude.toString())
                    .build()

            var request = Request.Builder()
                    .url(API_URL + "/post")
                    .addHeader("Cookie", cookie)
                    .post(requestBody)
                    .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(p0: Response?) {
                    cb(null, p0)
                }
                override fun onFailure(p0: Request?, p1: IOException?) {
                    cb(p0, null)
                }

            })
        }
    }
}