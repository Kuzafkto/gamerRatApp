package com.example.ratagamerapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface GiveawayApi {
@GET("giveaways")
suspend fun getAll(@Query("limit") limit:Int=20, @Query("offset") offset:Int=0):List<GiveawayApiModel>
    //limit y offset es para establecer limites y paginamiento de los resultados ¿como hago para que sea scroll infinito?
@GET("giveaway")
suspend fun getDetail(@Query("id")id:Int):GiveawayApiModel
}
@Singleton
class GiveawayService @Inject constructor(){
private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.gamerpower.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val api:GiveawayApi=retrofit.create(GiveawayApi::class.java)
}