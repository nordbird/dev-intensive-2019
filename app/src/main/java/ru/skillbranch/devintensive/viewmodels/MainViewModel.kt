package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val query = mutableLiveData("")
    private val chatRepository = ChatRepository
    private val chatItems = Transformations.map(chatRepository.loadChats()) { chats ->
        val (defChats, archiveChats) = chats.partition { !it.isArchived }

        val chatList = defChats
                .map { it.toChatItem() }
                .sortedBy { it.id.toInt() }

        if (archiveChats.isNotEmpty()) {
            val archiveChat = Chat("", "").apply { isArchived = true }
            archiveChats.forEach { archiveChat.messages.addAll(it.messages) }

            archiveChat.messages.sortBy { it.date }

            return@map listOf(archiveChat.toChatItem()) + chatList
        }

        return@map chatList
    }

    fun getChatData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chats = chatItems.value!!

            result.value = if (queryStr.isEmpty()) chats
            else chats.filter { it.title.contains(queryStr, true) }
        }

        result.addSource(chatItems) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }

    fun handleSearchQuery(text: String?) {
        query.value = text.orEmpty()
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

}