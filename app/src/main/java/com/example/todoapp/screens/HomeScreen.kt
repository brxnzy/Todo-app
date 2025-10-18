package com.example.todoapp.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.AuthViewModel
import com.example.todoapp.TaskViewModel
import com.example.todoapp.data.supabase

sealed class HomeTab(
    val title: String,
    val filledIcon: ImageVector,
    val outlineIcon: ImageVector
) {
    object Home :
        HomeTab(title = "Home", filledIcon = Icons.Filled.Home, outlineIcon = Icons.Outlined.Home)

    object Profile : HomeTab(
        title = "perfil",
        filledIcon = Icons.Filled.Person,
        outlineIcon = Icons.Outlined.Person
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    fabOnClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf<HomeTab>(value = HomeTab.Home) }
    val tabs = listOf(HomeTab.Home, HomeTab.Profile)
    val taskViewModel = remember { TaskViewModel(supabase) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == tab) tab.filledIcon else tab.outlineIcon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(text = tab.title) },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                is HomeTab.Home -> TaskScreen(taskViewModel)
                is HomeTab.Profile -> Text(text = "Profile")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    HomeScreen()
}
