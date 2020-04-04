package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.Date

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {

    fun unreadableMessageCount() = if (messages.isEmpty()) 0 else messages.filter { !it.isReaded }.size

    fun lastMessageDate(): Date? = if (messages.isEmpty()) null else messages.last().date

    fun lastMessageShort(): Pair<String, String?> {
        if (messages.isEmpty()) return "Сообщений нет" to null
        val from = messages.last().from.firstName
        val message = messages.last()
        if (message is TextMessage) {
            return message.text to from
        } else {
            return (message as ImageMessage).image to from
        }
    }

    private fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline,
                chatType = if (isArchived) ChatType.ARCHIVE else ChatType.SINGLE
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                if (isArchived) ChatType.ARCHIVE else ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }
}

enum class ChatType {
    SINGLE,
    GROUP,
    ARCHIVE
}