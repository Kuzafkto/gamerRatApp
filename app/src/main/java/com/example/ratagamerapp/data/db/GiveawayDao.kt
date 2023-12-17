package com.example.ratagamerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GiveawayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listGiveawayEntity: List<GiveawayEntity>)

    @Query("SELECT * FROM giveaway")
    fun getAll(): Flow<List<GiveawayEntity>>

    //nos obtenemos un giveaway especifico con todos sus comentarios
    // recuerda hacer un refresh de los comentarios para cuando el usuario
    // cree un comentario y así tener feedback instantaneo
    @Transaction
    @Query("SELECT * FROM giveaway WHERE id = :giveawayId")
    fun getById(giveawayId: Int): Flow<CommentGiveawayRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(giveawayEntity: GiveawayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun postComment(commentEntity: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(listGiveawayEntity: GiveawayEntity)
}