
package com.project.easynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.project.easynotes.ui.navigation.NavGraph
import com.project.easynotes.ui.theme.EasyNotesTheme
import com.project.easynotes.viewmodel.NotesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NotesViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val navController = rememberNavController()

            EasyNotesTheme(darkTheme = uiState.isDarkMode) {
                NavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}