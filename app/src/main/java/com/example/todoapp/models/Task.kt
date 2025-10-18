package com.example.todoapp.models

import kotlinx.datetime.DatePeriod
import kotlinx.serialization.Serializable


@Serializable
data class Task(
    val id:Int,
    val userId: String,
    val title:String,
    val description: String,
    val isComplete: Boolean,
    val priority: String,
    val date: String
)
