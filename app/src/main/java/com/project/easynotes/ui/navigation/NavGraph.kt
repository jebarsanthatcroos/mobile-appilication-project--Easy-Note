// ui/navigation/NavGraph.kt
package com.project.easynotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.easynotes.ui.screens.NotesListScreen
import com.project.easynotes.ui.screens.CreateEditNoteScreen
import com.project.easynotes.ui.screens.NoteDetailsScreen
import com.project.easynotes.ui.screens.CalendarScreen
import com.project.easynotes.ui.screens.TemplatesScreen
import com.project.easynotes.ui.screens.SettingsScreen
import com.project.easynotes.viewmodel.NotesViewModel

sealed class Screen(val route: String) {
    object NotesList : Screen("notes_list")
    object CreateNote : Screen("create_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
    object NoteDetails : Screen("note_details/{noteId}") {
        fun createRoute(noteId: Int) = "note_details/$noteId"
    }
    object Calendar : Screen("calendar")
    object Templates : Screen("templates")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: NotesViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesList.route
    ) {
        composable(Screen.NotesList.route) {
            NotesListScreen(
                viewModel = viewModel,
                onNavigateToCreate = { navController.navigate(Screen.CreateNote.route) },
                onNavigateToDetails = { noteId ->
                    navController.navigate(Screen.NoteDetails.createRoute(noteId))
                },
                onNavigateToCalendar = { navController.navigate(Screen.Calendar.route) },
                onNavigateToTemplates = { navController.navigate(Screen.Templates.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.CreateNote.route) {
            CreateEditNoteScreen(
                viewModel = viewModel,
                noteId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            CreateEditNoteScreen(
                viewModel = viewModel,
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.NoteDetails.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            NoteDetailsScreen(
                viewModel = viewModel,
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { navController.navigate(Screen.EditNote.createRoute(noteId)) }
            )
        }

        composable(Screen.Calendar.route) {
            CalendarScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNote = { noteId ->
                    navController.navigate(Screen.NoteDetails.createRoute(noteId))
                }
            )
        }

        composable(Screen.Templates.route) {
            TemplatesScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onCreateFromTemplate = { navController.navigate(Screen.CreateNote.route) }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}