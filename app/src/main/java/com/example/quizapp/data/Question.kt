package com.example.quizapp.data

// Data Class für eine Frage
data class Question(
    val id: Int,
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)