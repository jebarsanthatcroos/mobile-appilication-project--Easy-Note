package com.project.easynotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.easynotes.data.Note
import com.project.easynotes.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditNoteScreen(
    viewModel: NotesViewModel,
    noteId: Int?,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }
    var existingNote by remember { mutableStateOf<Note?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(noteId) {
        if (noteId != null) {
            existingNote = viewModel.getNoteById(noteId)
            existingNote?.let { note ->
                title = note.title
                content = note.content
                tag = note.tag
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (noteId == null) {
                                    viewModel.insertNote(
                                        Note(
                                            title = title,
                                            content = content,
                                            tag = tag
                                        )
                                    )
                                } else {
                                    existingNote?.let { note ->
                                        viewModel.updateNote(
                                            note.copy(
                                                title = title,
                                                content = content,
                                                tag = tag
                                            )
                                        )
                                    }
                                }
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Save, "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Title") },
                textStyle = MaterialTheme.typography.titleLarge,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tag,
                onValueChange = { tag = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Tag (optional)") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("Start typing...") },
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
