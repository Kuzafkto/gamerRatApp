package com.example.ratagamerapp.data.api

import com.example.ratagamerapp.data.db.GiveawayEntity

//el tipo de dato al que mapearemos siempre, contiene todos los datos importantes
data class GiveawayApiModel(
    val id: Int=0,
    val title: String="",
    //val worth:String,//porque puede contener N/A que significa 0
    val description: String="",
    val image:String="",
    val thumbnail: String="",
    val worth:String=""
)

//transforma una lista de apimodel en lista de entity
fun List<GiveawayApiModel>.asEntityModel(): List<GiveawayEntity> {
    return this.map {
        GiveawayEntity(
            it.id,
            it.title,
            it.description,
            it.thumbnail,
            it.image,
            false,
            it.worth
            )
    }
}

