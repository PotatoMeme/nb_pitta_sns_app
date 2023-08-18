package com.team8.pittasnsapp.model

data class User(
    val id: Int,
    val personalId : String,
    val personalPassword : String,
    val name: String,
    val message: String,
    val userImgUrl: String, // 이미지 Url
)