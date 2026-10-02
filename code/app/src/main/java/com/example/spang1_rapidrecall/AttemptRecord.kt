package com.example.spang1_rapidrecall

data class AttemptRecord(
    val sequenceLength: Int,
    val userGuess: String,
    val targetSequence: String,
    val isCorrect: Boolean,
    val timestamp: Long
)
