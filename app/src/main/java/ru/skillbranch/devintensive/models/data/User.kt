package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
        val id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
) {

    constructor(
            id: String,
            firstName: String?,
            lastName: String?
    ) : this(id = id, firstName = firstName, lastName = lastName, avatar = null)

    constructor(id: String) : this(id, firstName = "Ivan $id", lastName = "Petrov")

    init {
        println(
                "User created\n" +
                        "${if (firstName === "Vova") "FirstName $firstName LastName $lastName" else "LastName $lastName FirstName $firstName"}\n" +
                        "${getIntro()}"
        )

    }

    private fun getIntro() = """
        = = = = = = = = = =
         = = = = = = = = =
    """.trimIndent()

    fun toUserItem(): UserItem {
        val lastActivity = when {
            lastVisit == null -> "Еще ни разу не заходил"
            isOnline -> "online"
            else -> "Последний раз был ${lastVisit?.humanizeDiff()}"
        }
        return UserItem(
                id,
                "${firstName.orEmpty()} ${lastName.orEmpty()}",
                Utils.toInitials(firstName, lastName),
                avatar,
                lastActivity,
                false,
                isOnline
        )
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++

            val nameParts: List<String>? = fullName?.split(" ")

            val firstName = nameParts?.getOrNull(0)
            val lastName = nameParts?.getOrNull(1)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }

    class Builder(
            var userId: String = "0",
            var userFirstName: String? = null,
            var userLastName: String? = null,
            var userAvatar: String? = null,
            var userRating: Int = 0,
            var userRespect: Int = 0,
            var userLastVisit: Date? = Date(),
            var userIsOnline: Boolean = false
    ) {
        fun id(s: String): Builder {
            userId = s
            return this
        }

        fun firstName(s: String?): Builder {
            userFirstName = s
            return this
        }

        fun lastName(s: String?): Builder {
            userLastName = s
            return this
        }

        fun avatar(s: String?): Builder {
            userAvatar = s
            return this
        }

        fun rating(n: Int): Builder {
            userRating = n
            return this
        }

        fun respect(n: Int): Builder {
            userRespect = n
            return this
        }

        fun lastVisit(d: Date?): Builder {
            userLastVisit = d
            return this
        }

        fun isOnline(b: Boolean): Builder {
            userIsOnline = b
            return this
        }

        fun build(): User = User(userId,
                userFirstName,
                userLastName,
                userAvatar,
                userRating,
                userRespect,
                userLastVisit,
                userIsOnline)
    }
}