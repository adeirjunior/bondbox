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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.adeir.bondbox.viewmodel.FriendViewModel

@Composable
fun FriendsScreen(viewModel: FriendViewModel, navController: NavController) {
    val friends by viewModel.friends.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn {
            itemsIndexed(friends) { _, friend ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(friend.name, fontSize = 20.sp)
                        Text(friend.phone, fontSize = 16.sp)
                    }
                    Row {
                        IconButton(onClick = { navController.navigate("edit/${friend.id}") }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { viewModel.deleteFriend(friend) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }

        Button(onClick = { navController.navigate("add") }) { Text("Add Friend") }
    }
}
