package ru.skillbranch.devintensive.utils

import androidx.core.text.isDigitsOnly
import java.util.*

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question == Question.IDLE) {
            question.question to status.color
        } else {
            val res = question.isValidAnswer(answer)
            if (res.first) {
                if (question.answer.contains(answer.toLowerCase(Locale.getDefault()))) {
                    question = question.nextQuestion()
                    "Отлично - ты справился\n${question.question}" to status.color
                } else {
                    if (status == Status.CRITICAL) {
                        status = Status.NORMAL
                        question = Question.NAME
                        "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                    } else {
                        status = status.nextStatus()
                        "Это неправильный ответ\n${question.question}" to status.color
                    }
                }
            } else {
                "${res.second}\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun isValidAnswer(answer: String): Pair<Boolean, String> {
                val res = answer.isNotEmpty() && answer[0].isUpperCase()
                return Pair(res, if (res) "" else "Имя должно начинаться с заглавной буквы")
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun isValidAnswer(answer: String): Pair<Boolean, String> {
                val res = answer.isNotEmpty() && answer[0].isLowerCase()
                return Pair(res, if (res) "" else "Профессия должна начинаться со строчной буквы")
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun isValidAnswer(answer: String): Pair<Boolean, String> {
                val res = answer.isNotEmpty() && !answer.contains(Regex("\\d"))
                return Pair(res, if (res) "" else "Материал не должен содержать цифр")
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun isValidAnswer(answer: String): Pair<Boolean, String> {
                val res = answer.isNotEmpty() && answer.isDigitsOnly()
                return Pair(res, if (res) "" else "Год моего рождения должен содержать только цифры")
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun isValidAnswer(answer: String): Pair<Boolean, String> {
                val res = answer.isNotEmpty() && answer.isDigitsOnly() && (answer.length == 7)
                return Pair(res, if (res) "" else "Серийный номер содержит только цифры, и их 7")
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun isValidAnswer(answer: String): Pair<Boolean, String> = Pair(true, "")
        };

        abstract fun nextQuestion(): Question
        abstract fun isValidAnswer(answer: String): Pair<Boolean, String>
    }
}