package com.project.easynotes.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromNoteCategory(value: NoteCategory): String = value.name

    @TypeConverter
    fun toNoteCategory(value: String): NoteCategory = NoteCategory.valueOf(value)
}