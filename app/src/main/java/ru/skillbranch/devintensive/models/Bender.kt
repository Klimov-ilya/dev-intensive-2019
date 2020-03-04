package ru.skillbranch.devintensive.models

import android.annotation.SuppressLint

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion() = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    @SuppressLint("DefaultLocale")
    fun listenAnswer(answer: String) =
        if (question.checkAnswer(answer)) {
            when {
                question == Question.IDLE -> question.question to status.color
                question.answers.contains(answer.toLowerCase()) -> {
                    question = question.nextQuestion()
                    "Отлично - ты справился\n${question.question}" to status.color
                }
                else -> {
                    if (status != Status.CRITICAL) {
                        status = status.nextStatus()
                        "Это неправильный ответ\n${question.question}" to status.color
                    } else {
                        status = Status.NORMAL
                        question = Question.NAME
                        "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                    }
                }
            }
        } else {
            "${question.error}\n${question.question}" to status.color
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

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")){
            override val error = "Имя должно начинаться с заглавной буквы"

            override fun nextQuestion() = PROFESSION

            override fun checkAnswer(answer: String) = answer[0].isUpperCase() && answer.isNotEmpty()
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override val error = "Профессия должна начинаться со строчной буквы"

            override fun nextQuestion() = MATERIAL

            override fun checkAnswer(answer: String) = answer[0].isLowerCase() && answer.isNotEmpty()
        },
        MATERIAL("Из чего я сделан?", listOf("метал", "дерево", "metal", "iron", "wood")) {
            override val error = "Материал не должен содержать цифр"

            override fun nextQuestion() = BDAY

            override fun checkAnswer(answer: String) = !answer.matches("-?\\d+(\\.\\d+)?".toRegex())
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override val error = "Год моего рождения должен содержать только цифры"

            override fun nextQuestion() = SERIAL

            override fun checkAnswer(answer: String) = answer.matches("-?\\d+(\\.\\d+)?".toRegex())
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override val error: String = "Серийный номер содержит только цифры, и их 7"

            override fun nextQuestion() = IDLE

            override fun checkAnswer(answer: String) = answer.length == 7 && answer.matches("-?\\d+(\\.\\d+)?".toRegex())
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override val error: String = ""

            override fun nextQuestion() = IDLE

            override fun checkAnswer(answer: String) = true
        };

        abstract val error: String

        abstract fun nextQuestion(): Question

        abstract fun checkAnswer(answer: String): Boolean

    }
}