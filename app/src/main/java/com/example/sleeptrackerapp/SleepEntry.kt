package com.example.sleeptrackerapp

/**
 * Represents a single entry of sleep data.
 *
 * @property date The date when the sleep entry was recorded, formatted as a String (e.g., "YYYY-MM-DD").
 * @property sleepTime The time at which the user started sleeping, formatted as a String (e.g., "HH:MM AM/PM").
 * @property wakeUpTime The time at which the user woke up, formatted as a String (e.g., "HH:MM AM/PM").
 * @property duration The total duration of the sleep, formatted as a String (e.g., "X hours Y minutes").
 */
data class SleepEntry(
    val date: String,
    val sleepTime: String,
    val wakeUpTime: String,
    val duration: String
)