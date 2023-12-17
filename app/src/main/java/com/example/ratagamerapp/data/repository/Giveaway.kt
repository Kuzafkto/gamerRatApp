package com.example.ratagamerapp.data.repository

import com.example.ratagamerapp.data.api.GiveawayApiModel
import com.example.ratagamerapp.data.api.asEntityModel
import com.example.ratagamerapp.data.db.GiveawayEntity

//obtiene los datos como se van a mostrar en detail u
data class Giveaway(
    val id: Int=0,
    val title: String = "",
    val description:String="",
    val thumbnail: String ="",
    val image:String="",
    var inValueChecker: Boolean = false,
    val worth:String=""
)
fun Giveaway.asEntityModel(): GiveawayEntity {
    return GiveawayEntity(
           id,
        title,
        description,
        thumbnail,
        image,
        inValueChecker,
        worth
        )

}