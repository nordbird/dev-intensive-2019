package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
        val id: String,
        val title: String,
        val members: List<User> = listOf(),
        var messages: MutableList<BaseMessage> = mutableListOf(),
        var isArchived: Boolean = false
) {

    fun unreadableMessageCount(): Int = messages.filter { !it.isReaded }.size

    fun lastMessageDate(): Date? = messages.lastOrNull()?.date

    fun lastMessageShort(): Pair<String, String?> = when (val lastMessage = messages.lastOrNull()) {
        is TextMessage -> lastMessage.text.orEmpty() to lastMessage.from.firstName
        is ImageMessage -> "${lastMessage.from.firstName} отправил фото" to lastMessage.from.firstName
        else -> "" to ""
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
                    user.isOnline
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
                    ChatType.GROUP,
                    lastMessageShort().second
            )
        }
    }

    fun toArchiveItem(archiveChats: List<Chat>): ChatItem {
        val lastChat = archiveChats.sortedBy { it.lastMessageDate() }.last()
        return ChatItem(
                "-1",
                null,
                "",
                "Архив чатов",
                lastChat.lastMessageShort().first,
                archiveChats.sumBy { it.unreadableMessageCount() },
                lastChat.lastMessageDate()?.shortFormat(),
                false,
                ChatType.ARCHIVE,
                "@${lastChat.lastMessageShort().second}"
        )
    }


}

enum class ChatType {
    SINGLE,
    GROUP,
    ARCHIVE
}