package com.project.easynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.easynotes.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedCategory: NoteCategory? = null,
    val sortOrder: SortOrder = SortOrder.MODIFIED_DESC,
    val isDarkMode: Boolean = false
)

enum class SortOrder {
    MODIFIED_DESC,
    CREATED_DESC,
    TITLE_ASC
}

enum class NoteTemplate(val title: String, val content: String) {
    MEETING("Meeting Notes", "Date:\nAttendees:\nAgenda:\n\nNotes:\n\nAction Items:\n"),
    TODO("To-Do List", "☐ \n☐ \n☐ \n"),
    JOURNAL("Journal Entry", "Today I feel...\n\nWhat happened today:\n\nGrateful for:\n"),
    SHOPPING("Shopping List", "☐ \n☐ \n☐ \n")
}

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val flow = if (_uiState.value.selectedCategory != null) {
                repository.getNotesByCategory(_uiState.value.selectedCategory!!)
            } else {
                repository.getAllNotes()
            }

            flow.collect { notes ->
                _uiState.value = _uiState.value.copy(
                    notes = sortNotes(notes),
                    isLoading = false
                )
            }
        }
    }

    fun searchNotes(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isEmpty()) {
            loadNotes()
        } else {
            viewModelScope.launch {
                repository.searchNotes(query).collect { notes ->
                    _uiState.value = _uiState.value.copy(notes = sortNotes(notes))
                }
            }
        }
    }

    fun filterByCategory(category: NoteCategory?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        loadNotes()
    }

    fun setSortOrder(order: SortOrder) {
        _uiState.value = _uiState.value.copy(sortOrder = order)
        val sortedNotes = sortNotes(_uiState.value.notes)
        _uiState.value = _uiState.value.copy(notes = sortedNotes)
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(isDarkMode = !_uiState.value.isDarkMode)
    }

    private fun sortNotes(notes: List<Note>): List<Note> {
        val pinned = notes.filter { it.isPinned }
        val unpinned = notes.filter { !it.isPinned }

        val sortedUnpinned = when (_uiState.value.sortOrder) {
            SortOrder.MODIFIED_DESC -> unpinned.sortedByDescending { it.modifiedAt }
            SortOrder.CREATED_DESC -> unpinned.sortedByDescending { it.createdAt }
            SortOrder.TITLE_ASC -> unpinned.sortedBy { it.title }
        }

        return pinned + sortedUnpinned
    }

    suspend fun getNoteById(id: Int): Note? = repository.getNoteById(id)

    fun insertNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note.copy(modifiedAt = System.currentTimeMillis()))
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun togglePinNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note.copy(isPinned = !note.isPinned))
        }
    }

    fun createNoteFromTemplate(template: NoteTemplate, category: NoteCategory): Note {
        return Note(
            title = template.title,
            content = template.content,
            category = category,
            template = template.name
        )
    }
}
