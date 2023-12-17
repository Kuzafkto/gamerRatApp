package com.example.ratagamerapp.data.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiveawayDBRepository @Inject constructor(private val giveawayDao:GiveawayDao){

val allGiveaways: Flow<List<GiveawayEntity>> = giveawayDao.getAll()
//val giveawayDetail: Flow<GiveawayEntity> = giveawayDao.getById()
    @WorkerThread
    suspend fun insert(listGiveawayEntity: List<GiveawayEntity>){
    giveawayDao.insert(listGiveawayEntity)
    }

    @WorkerThread
    suspend fun getDetail(id:Int):Flow<CommentGiveawayRelation>{
        return giveawayDao.getById(id)
    }

    @WorkerThread
    suspend fun postComment(commentEntity: CommentEntity){
        giveawayDao.postComment(commentEntity)
    }
    @WorkerThread
    suspend fun insertOne(giveawayEntity: GiveawayEntity){
        giveawayDao.insertOne(giveawayEntity)
    }


}