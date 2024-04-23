package com.ahmadrd.instagramclone.models

data class User(
    var name: String? = null,
    var image: String? = null,
    var email: String? = null,
    var password: String? = null
) {

    constructor(name: String?, email: String?, password: String?):
    this("", name, email, password)

    constructor(email: String?, password: String?):
    this("", email, password)
}