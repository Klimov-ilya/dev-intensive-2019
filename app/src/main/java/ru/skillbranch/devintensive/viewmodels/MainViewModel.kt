package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()) { chat ->
        return@map chat.map { it.toChatItem() }.sortedBy { it.id.toInt() }
    }

    fun getChatData(): LiveData<List<ChatItem>> = chats

    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) = chatRepository.handlerSearchQuery(text)

}

/*val chatItemLD = MutableLiveData<List<ChatItem>>().apply {
            val chats = chatRepository.loadChats().value?.filter { !it.isArchived }
            val archivedChats = chatRepository.loadChats().value?.filter { it.isArchived }
            Log.d("==> ", "$archivedChats")
            value = chats?.map { it.toChatItem() }?.sortedBy { it.id.toInt() }
            if (archivedChats.isNullOrEmpty()) {

            } else {
                val archiveChatItem = ChatItem(
                    id = archivedChats.last().messages.last().id,
                    avatar = null,
                    initials = "",
                    title = "",
                    shortDescription = archivedChats.last().lastMessageShort().first,
                    messageCount = archivedChats.sumBy { it.unreadableMessageCount() },
                    lastMessageDate = archivedChats.last().lastMessageDate()?.shortFormat(),
                    isOnline = false,
                    chatType = ChatType.ARCHIVE,
                    author = archivedChats.last().messages.last().from.firstName
                )
                value = chats?.map { it.toChatItem() }?.sortedBy { it.id.toInt() }?.toMutableList()
                    ?.apply {
                        add(0, archiveChatItem)
                    }
            }
        }*/