package com.example.chatapplive

class MessageModel {

    var msgId:String?=null
    var senderId :String?=null
    var message:String?=null
    var timestamp: Long? = null

    constructor()

    constructor(msgId:String? ,senderId: String?, message:String?,timestamp : Long?)
    {
        this.message=message
        this.msgId=msgId
        this.senderId=senderId
        this.timestamp=timestamp
    }
}
