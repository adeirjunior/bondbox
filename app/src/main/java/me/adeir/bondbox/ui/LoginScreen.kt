package me.adeir.bondbox.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.adeir.bondbox.viewmodel.UserViewModel

@Composable
fun LoginScreen(viewModel: UserViewModel, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = viewModel.username, onValueChange = { viewModel.username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = viewModel.password, onValueChange = { viewModel.password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.login { navController.navigate("friends") } }) { Text("Login") }
        Button(onClick = { viewModel.register() }) { Text("Register") }
    }
}
