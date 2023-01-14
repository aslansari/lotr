package com.aslansari.lotr.feature.character.data

import com.aslansari.lotr.feature.character.data.model.CharacterListResponse
import retrofit2.http.GET

interface CharacterService {

    @GET("character")
    suspend fun getCharacterList(): CharacterListResponse
}