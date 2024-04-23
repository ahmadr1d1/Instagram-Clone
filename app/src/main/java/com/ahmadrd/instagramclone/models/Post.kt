package com.ahmadrd.instagramclone.models

data class Post(
    val postUrl: String = "",
    val caption: String = "",
    val uid: String = "",
    val time: String = ""
) {

    constructor(postUrl: String, caption: String):
    this(postUrl, caption, "", "")

}