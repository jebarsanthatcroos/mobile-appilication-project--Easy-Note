
package com.project.easynotes.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val category: NoteCategory = NoteCategory.PERSONAL,
    val isPinned: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis(),
    val reminderDate: Long? = null,
    val template: String? = null
)

enum class NoteCategory(val displayName: String, val color: Long) {
    PERSONAL("Personal", 0xFFA8D5BA),
    WORK("Work", 0xFFBEE3DB),
    PROJECTS("Projects", 0xFFFFF4B2),
    IDEAS("Ideas", 0xFFFFD6E8),
    SHOPPING("Shopping", 0xFFE0BBE4)
}



