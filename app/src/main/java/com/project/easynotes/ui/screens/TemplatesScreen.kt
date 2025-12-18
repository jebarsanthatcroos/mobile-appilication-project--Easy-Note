package com.project.easynotes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.easynotes.data.NoteCategory
import com.project.easynotes.viewmodel.NoteTemplate
import com.project.easynotes.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesScreen(
    viewModel: NotesViewModel,
    onNavigateBack: () -> Unit,
    onCreateFromTemplate: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Templates") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                "Quick Start Templates",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Choose a template to get started quickly",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        TemplateItem(
                            template = NoteTemplate.MEETING,
                            icon = Icons.Default.People,
                            description = "Meeting agenda and notes"
                        ),
                        TemplateItem(
                            template = NoteTemplate.TODO,
                            icon = Icons.Default.CheckCircle,
                            description = "Task checklist"
                        ),
                        TemplateItem(
                            template = NoteTemplate.JOURNAL,
                            icon = Icons.Default.MenuBook,
                            description = "Daily journal entry"
                        ),
                        TemplateItem(
                            template = NoteTemplate.SHOPPING,
                            icon = Icons.Default.ShoppingCart,
                            description = "Shopping list"
                        )
                    )
                ) { item ->
                    TemplateCard(
                        item = item,
                        onClick = {
                            val note = viewModel.createNoteFromTemplate(
                                item.template,
                                NoteCategory.PERSONAL
                            )
                            viewModel.insertNote(note)
                            onCreateFromTemplate()
                        }
                    )
                }
            }
        }
    }
}

data class TemplateItem(
    val template: NoteTemplate,
    val icon: ImageVector,
    val description: String
)

@Composable
fun TemplateCard(item: TemplateItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                item.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                item.template.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
