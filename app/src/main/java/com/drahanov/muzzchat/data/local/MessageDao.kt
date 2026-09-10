package com.drahanov.muzzchat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY timestamp, id")
    fun observeMessages(): Flow<List<MessageEntity>>

    @Insert
    suspend fun insert(message: MessageEntity)
}
