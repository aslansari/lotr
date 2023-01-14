package com.aslansari.lotr.feature.character.data

import com.aslansari.lotr.architecture.domain.Resource
import com.aslansari.lotr.architecture.domain.asResource
import com.aslansari.lotr.feature.character.data.model.CharacterDTO
import com.aslansari.lotr.feature.character.data.model.toModel
import com.aslansari.lotr.feature.character.list.ui.LotrCharacter
import com.aslansari.lotr.network.di.Dispatcher
import com.aslansari.lotr.network.di.LotrDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    @Dispatcher(LotrDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val characterService: CharacterService,
) : CharacterRepository {
    override fun getCharacterList(): Flow<Resource<List<LotrCharacter>>> {
        return flow {
            val response = characterService.getCharacterList()
            emit(response.docs.map(CharacterDTO::toModel))
        }.asResource().flowOn(ioDispatcher)
    }
}