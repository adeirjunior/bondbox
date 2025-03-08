package me.adeir.bondbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.adeir.bondbox.data.dao.FriendDao
import me.adeir.bondbox.data.dao.UserDao
import me.adeir.bondbox.data.model.Friend
import me.adeir.bondbox.data.model.User

@Database(entities = [User::class, Friend::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
