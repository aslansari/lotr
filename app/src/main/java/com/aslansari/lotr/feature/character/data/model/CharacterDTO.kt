package com.aslansari.lotr.feature.character.data.model

import com.aslansari.lotr.feature.character.list.ui.LotrCharacter
import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("race")
    val race: String,
    @SerializedName("wikiUrl")
    val wikiUrl: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("death")
    val death: String,
)

fun CharacterDTO.toModel(): LotrCharacter {
    return LotrCharacter(
        name = this.name
    )
}