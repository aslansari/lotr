package com.aslansari.lotr.feature.character.data

import com.aslansari.lotr.architecture.domain.Resource
import com.aslansari.lotr.feature.character.list.ui.LotrCharacter
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacterList() : Flow<Resource<List<LotrCharacter>>>
}