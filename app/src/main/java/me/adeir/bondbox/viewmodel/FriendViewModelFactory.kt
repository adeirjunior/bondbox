package me.adeir.bondbox.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FriendViewModelFactory(
    private val application: Application,
    private val userId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(application, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
