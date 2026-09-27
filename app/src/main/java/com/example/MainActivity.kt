package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.UserProgress
import com.example.ui.components.AITutorBottomSheet
import com.example.ui.components.AppTopBar
import com.example.ui.screens.*
import com.example.ui.theme.AIMLAcademyTheme
import com.example.ui.theme.CyanPrimary

enum class AppScreen(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    LEARN("Learn", Icons.Default.School),
    LABS("Labs", Icons.Default.Science),
    PROJECTS("Projects", Icons.Default.RocketLaunch),
    PROFILE("Profile", Icons.Default.Person)
}

enum class SecondaryScreen {
    NONE,
    TOOLBOX,
    RESEARCH
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }
            var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
            var secondaryScreen by remember { mutableStateOf(SecondaryScreen.NONE) }
            var showAITutor by remember { mutableStateOf(false) }
            var labsInitialTab by remember { mutableStateOf(0) }

            var userProgress by remember {
                mutableStateOf(UserProgress())
            }

            AIMLAcademyTheme(darkTheme = isDarkTheme) {
                // Secondary Screen Overlays
                when (secondaryScreen) {
                    SecondaryScreen.TOOLBOX -> {
                        BackHandler { secondaryScreen = SecondaryScreen.NONE }
                        ToolboxScreen(onBack = { secondaryScreen = SecondaryScreen.NONE })
                    }
                    SecondaryScreen.RESEARCH -> {
                        BackHandler { secondaryScreen = SecondaryScreen.NONE }
                        ResearchScreen(onBack = { secondaryScreen = SecondaryScreen.NONE })
                    }
                    SecondaryScreen.NONE -> {
                        // Main Bottom Navigation Scaffold
                        if (currentScreen != AppScreen.HOME) {
                            BackHandler { currentScreen = AppScreen.HOME }
                        }

                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("main_scaffold"),
                            topBar = {
                                AppTopBar(
                                    currentScreenTitle = currentScreen.title,
                                    xp = userProgress.xp,
                                    streak = userProgress.streakDays,
                                    isDarkTheme = isDarkTheme,
                                    onToggleTheme = { isDarkTheme = !isDarkTheme },
                                    onOpenTutor = { showAITutor = true },
                                    onOpenSearch = { secondaryScreen = SecondaryScreen.RESEARCH }
                                )
                            },
                            bottomBar = {
                                NavigationBar(
                                    modifier = Modifier.testTag("bottom_navigation_bar"),
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    tonalElevation = 8.dp
                                ) {
                                    AppScreen.values().forEach { screen ->
                                        NavigationBarItem(
                                            selected = currentScreen == screen,
                                            onClick = {
                                                currentScreen = screen
                                                if (screen == AppScreen.LABS) {
                                                    labsInitialTab = 0
                                                }
                                            },
                                            icon = {
                                                Icon(
                                                    imageVector = screen.icon,
                                                    contentDescription = screen.title
                                                )
                                            },
                                            label = { Text(screen.title) },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                                indicatorColor = CyanPrimary
                                            ),
                                            modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                                        )
                                    }
                                }
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                when (currentScreen) {
                                    AppScreen.HOME -> HomeScreen(
                                        userProgress = userProgress,
                                        onNavigateToLearn = { currentScreen = AppScreen.LEARN },
                                        onNavigateToLabs = { tabIndex ->
                                            labsInitialTab = tabIndex
                                            currentScreen = AppScreen.LABS
                                        },
                                        onNavigateToProjects = { currentScreen = AppScreen.PROJECTS },
                                        onOpenToolbox = { secondaryScreen = SecondaryScreen.TOOLBOX },
                                        onOpenResearch = { secondaryScreen = SecondaryScreen.RESEARCH }
                                    )
                                    AppScreen.LEARN -> LearnScreen(
                                        completedLessonIds = userProgress.completedLessonIds,
                                        onCompleteLesson = { lessonId ->
                                            userProgress = userProgress.copy(
                                                completedLessonIds = userProgress.completedLessonIds + lessonId,
                                                xp = userProgress.xp + 100,
                                                learningHours = userProgress.learningHours + 0.5f
                                            )
                                        }
                                    )
                                    AppScreen.LABS -> LabsScreen(
                                        initialTab = labsInitialTab
                                    )
                                    AppScreen.PROJECTS -> ProjectsScreen()
                                    AppScreen.PROFILE -> ProfileScreen(
                                        userProgress = userProgress,
                                        isDarkTheme = isDarkTheme,
                                        onToggleTheme = { isDarkTheme = !isDarkTheme }
                                    )
                                }
                            }

                            // AI Tutor Bottom Sheet
                            if (showAITutor) {
                                AITutorBottomSheet(
                                    onDismiss = { showAITutor = false }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
