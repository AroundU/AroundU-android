package io.lassondehacks.aroundu_android.domain

import java.util.*

data class Post(
        var id: String?,
        var user: User?,
        var parent: Post?,
        var media: Media,
        var description: String,
        var latitude: Float,
        var longitude: Float,
        var timestamp: Date,
        var upvotes: Int,
        var downvotes: Int,
        var comments: MutableList<Post>,
        var upvoted: Boolean
)