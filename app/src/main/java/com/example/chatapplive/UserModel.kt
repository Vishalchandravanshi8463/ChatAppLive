package com.example.chatapplive

data class UserModel(
    var userId: String = "",  // Initialize with default values if needed
    var name: String = "",
    var email: String = "",
    var password: String = ""
)