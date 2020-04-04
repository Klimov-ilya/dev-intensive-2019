package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val query = mutableLiveData("")

    fun getChatData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()
        val chatItemLD = MutableLiveData<List<ChatItem>>().apply {
            value = chatRepository.loadChats().value?.filter { !it.isArchived }?.map { it.toChatItem() }?.sortedBy { it.id.toInt() }
        }

        val filterF = {
            val query = query.value!!
            val chats = chatItemLD.value!!
            result.value = if (query.isEmpty()) chats else chats.filter { chat -> chat.title.contains(query, true)  }
        }

        result.addSource(query) { filterF.invoke() }
        result.addSource(chatItemLD) { filterF.invoke() }

        return result
    }

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

    fun handleSearchQuery(text: String) {
        query.value = text
    }

}