package kz.iitu.chatapp

data class Message(
    val senderId : String,
    val name : String,
    val surname: String,
    val message : String,
    val date : String
) {
    constructor(): this("","","","","")
}