package com.aslansari.lotr.feature.character.data.model

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("docs")
    val docs: List<CharacterDTO>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
)
