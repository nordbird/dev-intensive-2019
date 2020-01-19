package ru.skillbranch.devintensive.models

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
}