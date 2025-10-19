package com.example.todoapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.TaskViewModel
import com.example.todoapp.data.supabase
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf<HomeTab>(value = HomeTab.Home) }
    val tabs = listOf(HomeTab.Home, HomeTab.Profile)
    val taskViewModel = remember { TaskViewModel(supabase) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(value = false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(horizontal = 17.dp, vertical = 9.dp)
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
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
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

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.9f)
                    .padding(all = 24.dp)
            ) {
                CreateNoteScreen {
                    scope.launch {
                        sheetState.expand()
                    }
                        .invokeOnCompletion { showSheet = false }
                }
            }
        }
    }
}

@Composable
fun PriorityChips() {
    var priority by remember { mutableStateOf(value = "Normal") }

    val options = listOf("Importante", "Normal", "Casual")

    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Text(
            text = "Prioridad:", style = TextStyle(
                fontSize = 16.sp,
            )
        )
        options.forEach { option ->
            val selectedColor = when (option) {
                "Importante" -> Color(color = 0xFFE53935)
                "Normal" -> Color(color = 0xFFFFC107)
                "Casual" -> Color(color = 0xFF4CAF50)
                else -> Color.Gray
            }

            val chipColors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = selectedColor,
                selectedLabelColor = Color.White,
                containerColor = Color.LightGray.copy(alpha = 0.3f),
                labelColor = Color.DarkGray
            )

            FilterChip(
                selected = priority == option,
                onClick = { priority = option },
                label = { Text(text = option) },
                colors = chipColors
            )

        }
    }
}

@Composable
fun CreateNoteScreen(onClose: () -> Unit = {}) {
    var title by remember { mutableStateOf(value = "") }
    var description by remember { mutableStateOf(value = "") }

    var pressed by remember { mutableStateOf(value = false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 0.95f else 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crea una nota",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                letterSpacing = 0.15.sp,
                lineHeight = 24.sp
            ),
            color = Color.DarkGray,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(height = 10.dp))

        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text(
                    text = "Titulo", style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Create, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )

        TextField(
            value = description,
            onValueChange = {
                description = it
            },
            label = {
                Text(
                    text = "Descripcion", style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )

        PriorityChips()

        TextButton(
            onClick = { /* TODO() not yet implemented */ },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color.DarkGray
            ),
            modifier = Modifier
                .scale(scale)
                .padding(all = 4.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(size = 28.dp)
                )
                Text(text = "Crear tarea", fontSize = 14.sp)
            }
        }

    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    HomeScreen()
}
