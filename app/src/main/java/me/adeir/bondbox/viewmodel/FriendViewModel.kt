package me.adeir.bondbox.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.adeir.bondbox.data.AppDatabase
import me.adeir.bondbox.data.model.Friend

class FriendViewModel(application: Application, private val userId: Int) : AndroidViewModel(application) {
    private val friendDao = AppDatabase.getDatabase(application).friendDao()

    val friends = friendDao.getFriends(userId).stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addFriend(name: String, phone: String) {
        viewModelScope.launch {
            friendDao.insert(Friend(userId = userId, name = name, phone = phone))
        }
    }

    fun updateFriend(friend: Friend) {
        viewModelScope.launch {
            friendDao.update(friend)
        }
    }

    fun deleteFriend(friend: Friend) {
        viewModelScope.launch {
            friendDao.delete(friend)
        }
    }
}
