package com.project.easynotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.easynotes.viewmodel.NotesViewModel
import com.project.easynotes.viewmodel.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: NotesViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var expandedSort by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
            Text(
                text = "Appearance",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Mode")
                Switch(
                    checked = uiState.isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Sorting",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expandedSort,
                onExpandedChange = { expandedSort = !expandedSort }
            ) {
                OutlinedTextField(
                    value = when (uiState.sortOrder) {
                        SortOrder.MODIFIED_DESC -> "Last Modified"
                        SortOrder.CREATED_DESC -> "Date Created"
                        SortOrder.TITLE_ASC -> "Title (A-Z)"
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sort by") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSort) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedSort,
                    onDismissRequest = { expandedSort = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Last Modified") },
                        onClick = {
                            viewModel.setSortOrder(SortOrder.MODIFIED_DESC)
                            expandedSort = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Date Created") },
                        onClick = {
                            viewModel.setSortOrder(SortOrder.CREATED_DESC)
                            expandedSort = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Title (A-Z)") },
                        onClick = {
                            viewModel.setSortOrder(SortOrder.TITLE_ASC)
                            expandedSort = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Easy Notes v1.0",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "A simple and elegant note-taking app",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
