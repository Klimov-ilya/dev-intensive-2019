package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()
    private val query = mutableLiveData("")

    fun loadChats(): MutableLiveData<List<Chat>> {
        val result = MediatorLiveData<List<Chat>>()

        val filterF = {
            val query = query.value!!
            val chats = chats.value!!
            result.value = if (query.isEmpty()) chats else chats.filter { chat ->
                chat.toChatItem().title.contains(query, true)
            }
        }

        result.addSource(query) { filterF.invoke() }
        result.addSource(chats) { filterF.invoke() }

        return result
    }

    // Получение чата из всего списка по айди
    fun find(chatId: String): Chat? =
        chats.value!!.getOrNull(chats.value!!.indexOfFirst { it.id == chatId })

    // Обновление чата
    fun update(item: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind =  chats.value!!.indexOfFirst { it.id == item.id }
        if (ind == -1) return
        copy[ind] = item
        chats.value = copy
    }

    fun handlerSearchQuery(text: String) {
        query.value = text
    }
}