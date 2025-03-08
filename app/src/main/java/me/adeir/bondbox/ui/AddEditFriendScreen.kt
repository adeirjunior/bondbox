package me.adeir.bondbox.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.adeir.bondbox.viewmodel.AddEditFriendViewModel
import me.adeir.bondbox.viewmodel.UserViewModel

@Composable
fun AddEditFriendScreen(
    viewModel: AddEditFriendViewModel,
    userViewModel: UserViewModel,
    navController: NavController,
    friendId: Int?
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    val context = LocalContext.current // Acessa o contexto para mostrar Toasts

    // Observar o estado do usuário logado
    val loggedInUser by userViewModel.loggedInUser.collectAsState()

    LaunchedEffect(friendId) {
        friendId?.let {
            val friend = viewModel.getFriend(it)
            friend?.let {
                name = it.name
                phone = it.phone
                isEditing = true
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = if (isEditing) "Edit Friend" else "Add Friend", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }

            Button(onClick = {
                // Verificar se o usuário está logado com base no estado do loggedInUser
                loggedInUser?.let { user ->
                    try {
                        if (isEditing) {
                            viewModel.updateFriend(friendId!!, user.id, name, phone)
                            Toast.makeText(context, "Friend updated successfully!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.addFriend(user.id, name, phone)
                            Toast.makeText(context, "Friend added successfully!", Toast.LENGTH_SHORT).show()
                        }

                        navController.popBackStack()
                    } catch (e: Exception) {
                        Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } ?: run {
                    Toast.makeText(context, "User not logged in", Toast.LENGTH_LONG).show()
                }
            }) {
                Text(if (isEditing) "Update" else "Save")
            }
        }
    }
}
