package me.adeir.bondbox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.adeir.bondbox.data.model.Friend

@Dao
interface FriendDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend: Friend)

    @Query("SELECT * FROM friends WHERE userId = :userId")
    fun getFriends(userId: Int): Flow<List<Friend>>

    @Query("SELECT * FROM friends WHERE id = :id")
    fun getFriendById(id: Int): Friend?

    @Update
    suspend fun update(friend: Friend)

    @Delete
    suspend fun delete(friend: Friend)
}
