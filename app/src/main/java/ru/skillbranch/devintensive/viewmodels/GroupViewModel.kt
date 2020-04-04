package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel : ViewModel() {
    private val query = mutableLiveData("")
    private val groupRepository = GroupRepository
    private val userItem = mutableLiveData(loadUsers())
    private val selectedItems = Transformations.map(userItem) { users ->
        users.filter { it.isSelected }
    }

    fun getUsersData(): LiveData<List<UserItem>> {
        val result = MediatorLiveData<List<UserItem>>()
        val filterF = {
            val queryString = query.value!!
            val users = userItem.value!!

            result.value = if (queryString.isEmpty()) users else users.filter { it.fullName.contains(queryString, true) }
        }
        result.addSource(userItem) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

    fun getSelectedData(): LiveData<List<UserItem>> {
        return selectedItems
    }

    fun handleSelectedItem(id: String) {
        userItem.value = userItem.value!!.map {
            if (it.id == id) it.copy(isSelected = !it.isSelected) else it
        }
    }

    fun handleRemoveChip(id: String) {
        userItem.value = userItem.value!!.map {
            if (it.id == id) it.copy(isSelected = false) else it
        }
    }

    private fun loadUsers(): List<UserItem> = groupRepository.loadUsers().map {
        it.toUserItem()
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    fun handleCreateGroup() {
        groupRepository.createChat(selectedItems.value!!)
    }
}