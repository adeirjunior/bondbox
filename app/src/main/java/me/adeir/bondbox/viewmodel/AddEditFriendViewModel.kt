package me.adeir.bondbox.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.adeir.bondbox.data.AppDatabase
import me.adeir.bondbox.data.model.Friend

class AddEditFriendViewModel(application: Application) : AndroidViewModel(application) {
    private val friendDao = AppDatabase.getDatabase(application).friendDao()

    private val userViewModel = UserViewModel(application)

    fun getCurrentUserId(): Int? {
        return userViewModel.loggedInUser.value?.id
    }

    fun getFriend(id: Int): Friend? {
        return friendDao.getFriendById(id)
    }

    fun addFriend(userId: Int, name: String, phone: String) {
        viewModelScope.launch {
            friendDao.insert(Friend(userId = userId, name = name, phone = phone))
        }
    }

    fun updateFriend(id: Int, userId: Int, name: String, phone: String) {
        viewModelScope.launch {
            friendDao.update(Friend(id = id, userId = userId, name = name, phone = phone))
        }
    }
}
