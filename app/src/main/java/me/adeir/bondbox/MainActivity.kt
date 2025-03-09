package me.adeir.bondbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.adeir.bondbox.ui.FriendsScreen
import me.adeir.bondbox.ui.LoginScreen
import me.adeir.bondbox.viewmodel.FriendViewModel
import me.adeir.bondbox.viewmodel.FriendViewModelFactory
import me.adeir.bondbox.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.adeir.bondbox.ui.AddEditFriendScreen
import me.adeir.bondbox.viewmodel.AddEditFriendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val userViewModel: UserViewModel = viewModel()

            val loggedInUser by userViewModel.loggedInUser.collectAsState()

            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(userViewModel, navController) }
                composable("friends") {
                    loggedInUser?.let { user ->
                        val friendViewModel: FriendViewModel = viewModel(
                            factory = FriendViewModelFactory(application, user.id)
                        )
                        FriendsScreen(friendViewModel, userViewModel, navController)
                    }
                }
                composable("add") {
                    val viewModel: AddEditFriendViewModel = viewModel()
                    AddEditFriendScreen(viewModel, userViewModel, navController, friendId = null)
                }
                composable("edit/{friendId}") { backStackEntry ->
                    val viewModel: AddEditFriendViewModel = viewModel()
                    val friendId = backStackEntry.arguments?.getString("friendId")?.toIntOrNull()
                    AddEditFriendScreen(viewModel, userViewModel, navController, friendId)
                }

            }
        }
    }
}
