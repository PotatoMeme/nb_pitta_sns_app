package com.team8.pittasnsapp

import com.team8.pittasnsapp.model.Post
import com.team8.pittasnsapp.model.User
import java.util.Random

object SampleData {
    val userArrayList: ArrayList<User> = arrayListOf<User>().apply {
        add(User(0, "신짱구"))
        add(User(1, "봉미선"))
        add(User(2, "신형만"))
        add(User(3, "신짱아"))
        add(User(4, "흰둥이"))
        add(User(5, "원장선생님"))
        add(User(6, "채성아선생님"))
        add(User(7, "나미리선생님"))
        add(User(8, "차은주선생님"))
        add(User(9, "훈이"))
        add(User(10, "철수"))
        add(User(11, "맹구"))
        add(User(12, "유리"))
        add(User(13, "장미"))
        add(User(14, "액션가면"))
    }

    private var countUserIdx = 14
    fun addUser(name: String, personalId: String, passWord: String) {
        val nextId = ++countUserIdx
        userArrayList.add(User(nextId, name))
    }

    val postArrayList: ArrayList<Post> = arrayListOf<Post>().apply {
        val random = Random()
        repeat(40) { id ->
            this.add(
                Post(
                    id,
                    userArrayList[random.nextInt(userArrayList.size)],
                    "test description $id".repeat(20)
                )
            )
        }
    }

    private var countPostIdx = 39
    fun addPost(userId: Int, description: String) {
        val nextId = ++countPostIdx
        postArrayList.add(Post(nextId, userArrayList[userId], description))
    }
}