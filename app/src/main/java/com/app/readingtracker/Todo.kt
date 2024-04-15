package com.app.readingtracker

import java.time.Instant
import java.util.*

data class Todo(
    var id: Int,
    var title: String,
    var createAt: Date
)

fun  vgetFakeTodo(): List<Todo> {
    return listOf<Todo>(
        Todo(1, "First task", Date.from(Instant.now())),
        Todo(2, "Second task", Date.from(Instant.now())),
        Todo(3, "Third task", Date.from(Instant.now())),
        Todo(4, "Fourth task", Date.from(Instant.now())),
        Todo(5, "Fifth task", Date.from(Instant.now())),
        Todo(6, "Sixth task", Date.from(Instant.now())),
        Todo(7, "Seventh task", Date.from(Instant.now())),
        Todo(8, "Eighth task", Date.from(Instant.now())),
    )
}
