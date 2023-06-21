package com.example.shedula_next_try.Model

data class CalendarEntry(
    val date: String,
    val workingHours: Double,
    val vacationDays: Int
)

class CalendarUtils {
    private val entries: MutableMap<String, MutableList<CalendarEntry>> = mutableMapOf()

    fun addEntry(date: String, workingHours: Double, vacationDays: Int) {
        val entry = CalendarEntry(date, workingHours, vacationDays)
        if (entries.containsKey(date)) {
            entries[date]?.add(entry)
        } else {
            entries[date] = mutableListOf(entry)
        }
    }

    fun getEntries(date: String): List<CalendarEntry> {
        return entries[date] ?: emptyList()
    }

    fun getAllEntries(): List<CalendarEntry> {
        val allEntries = mutableListOf<CalendarEntry>()
        for (entryList in entries.values) {
            allEntries.addAll(entryList)
        }
        return allEntries
    }

    fun clearEntries() {
        entries.clear()
    }
}
