package com.team8.pittasnsapp.model

data class Post(
    val id : Int,
    val user : User,
    val description: String,
    //val time: String,
    val postImgUrl : String // 이미지 Url
)