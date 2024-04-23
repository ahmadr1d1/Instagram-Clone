package com.ahmadrd.instagramclone.models

data class Reels(
    var reelsUrl: String = "",
    var caption: String = "",
    var profileLink: String? = null
) {

    constructor(reelsUrl: String, caption: String):
            this(reelsUrl, caption,"")

}