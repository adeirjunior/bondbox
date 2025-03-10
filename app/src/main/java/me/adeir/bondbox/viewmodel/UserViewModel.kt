package me.adeir.bondbox.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.adeir.bondbox.data.AppDatabase
import me.adeir.bondbox.data.model.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    fun register() {
        viewModelScope.launch {
            val existingUser = userDao.getUser()
            if (existingUser == null) {
                userDao.insertUser(User(username = username, password = password))
            } else {
                Log.d("UserViewModel", "Já existe um usuário registrado.")
            }
        }
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUser()
            if (user != null && user.password == password) {
                _loggedInUser.value = user
                onSuccess()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loggedInUser.value = null
            username = ""
            password = ""
        }
    }
}