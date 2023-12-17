package com.example.ratagamerapp.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.ratagamerapp.data.repository.Giveaway

@Entity(tableName = "giveaway")
data class GiveawayEntity(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val title: String,
    val description:String,
    val thumbnail: String,
    val image:String,
    var inValueChecker: Boolean = false,
    val worth:String
)



//esta class tiene todos los comentarios asociados a un juego
//establecemos la relacion entre tablas usando la tabla intermedia
data class CommentGiveawayRelation(
    @Embedded val giveaway:GiveawayEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId",
    )
    val gameComments:List<CommentEntity>//aca tiene que ir la clase Comment
)

fun List<GiveawayEntity>.asGiveaway(): List<Giveaway> {
    return this.map {
        Giveaway(
            it.id,
            it.title.replaceFirstChar { c -> c.uppercase() }, //inutil ya que los titulos si nos llegan bien en nuestra api pero ¿por las dudas?
            it.description,
            it.thumbnail,
            it.image,
            it.inValueChecker,
            it.worth
            )
    }


}
