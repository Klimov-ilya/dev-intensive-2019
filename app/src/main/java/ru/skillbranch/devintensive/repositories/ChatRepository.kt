package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.CacheManager
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats(): MutableLiveData<List<Chat>> = chats

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
}