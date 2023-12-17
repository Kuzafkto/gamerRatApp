package com.example.ratagamerapp.data.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiveawayApiRepository @Inject constructor(private val service: GiveawayService){
    //Este es el repositorio de la api, tiene las funciones que requieren el servicio que se contacta con la api
    suspend fun getAll():List<GiveawayApiModel> {

val simpleList=service.api.getAll(300,0)
        val giveawayListApiModel=simpleList
        return giveawayListApiModel
    }


}