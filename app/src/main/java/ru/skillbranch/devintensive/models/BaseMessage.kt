package ru.skillbranch.devintensive.models

import java.util.Date

abstract class BaseMessage (
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming : Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1

        fun makeMessage(from: User?,chat: Chat, date: Date = Date(), type: String = "text", payload: Any?) : BaseMessage {
            lastId++
            return when(type){
                "text" -> TextMessage(id = "$lastId", from = from, chat = chat, date = date, text = payload as String)
                else -> ImageMessage(id = "$lastId", from = from, chat = chat, date = date, image = payload as String)
            }
        }
    }

}