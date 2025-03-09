package me.adeir.bondbox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.adeir.bondbox.viewmodel.FriendViewModel
import me.adeir.bondbox.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(friendViewModel: FriendViewModel, userViewModel: UserViewModel, navController: NavController) {
    val friends by friendViewModel.friends.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header (TopAppBar)
        TopAppBar(
            title = { Text("Friends") },
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "User Menu")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit Profile") },
                        onClick = {
                            showMenu = false
                            navController.navigate("edit_user")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            showMenu = false
                            coroutineScope.launch {
                                userViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("friends") { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
        )

        // Lista de amigos
        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            itemsIndexed(friends) { _, friend ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(friend.name, fontSize = 20.sp)
                        Text(friend.phone, fontSize = 16.sp)
                    }
                    Row {
                        IconButton(onClick = { navController.navigate("edit/${friend.id}") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { friendViewModel.deleteFriend(friend) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        // Botão de adicionar amigo
        Button(
            onClick = { navController.navigate("add") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Add Friend")
        }
    }
}
