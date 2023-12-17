package com.example.ratagamerapp.data.repository

import android.util.Log
import com.example.ratagamerapp.data.api.GiveawayApiRepository
import com.example.ratagamerapp.data.api.asEntityModel
import com.example.ratagamerapp.data.db.CommentEntity
import com.example.ratagamerapp.data.db.GiveawayDBRepository
import com.example.ratagamerapp.data.db.asGiveaway
import com.example.ratagamerapp.data.db.CommentGiveawayRelation
import com.example.ratagamerapp.data.db.GiveawayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//este es el repositorio, tiene un singleton porque solo debe haber una instancia del mismo
@Singleton
class GiveawayRepository @Inject constructor(
    //el repositorio es el encargado de manejar tanto el repositorio de la base de datos como el repositorio de la api

    //NOTA: Recuerda siempre manejar ambas cosas al mismo tiempo para garantizar datos en caso de perdida de internet
    private val dbRepository: GiveawayDBRepository,
    private val apiRepository: GiveawayApiRepository
) {
//aca por ejemplo tenemos una variable la cual es un getall de todos los giveaways
    val giveaway: Flow<List<Giveaway>>
        get() {
            val list = dbRepository.allGiveaways.map {
                it.asGiveaway()
            }
            return list
        }

    //aca tenemos una funcion para recargar la lista, la cual llama a la api y hace una insert a la bbdd
    suspend fun refreshList() {
        //cualquier operacion que iteractue con la bbdd o api debe usar este
        //context con el fin de no afectar la interfaz de usuario
        withContext(Dispatchers.IO) {
            val apiGiveaway = apiRepository.getAll()
            dbRepository.insert(apiGiveaway.asEntityModel())
        }
    }

    suspend fun updateValueChecker(give: Giveaway) {
            give.inValueChecker=true
            var mapped=give.asEntityModel()
        withContext(Dispatchers.IO) {
            dbRepository.insertOne(mapped)
        }

    }





    /*esta funcion recibe un Id y devuelve un Flow (asincrono) GiveawayEntity, devolvemos el detail
           DE LA BASE DE DATOS por si en el caso que no hay internet al menos esta en la bbdd
     */
    suspend fun getDetail(id:Int):Flow<CommentGiveawayRelation>{
        return dbRepository.getDetail(id)
    }

    suspend fun postComment(commentEntity: CommentEntity){
        withContext(Dispatchers.IO){
            dbRepository.postComment(commentEntity)
        }
    }

    val valueChecker: Flow<List<GiveawayEntity>>
        get() = dbRepository.allGiveaways
            .map { giveaways ->
                giveaways.filter { it.inValueChecker }
            }

}