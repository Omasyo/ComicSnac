package com.keetr.comicsnac.network.character.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.CharacterListApiModel

typealias CharacterDetailsResponse = ResponseApiModel<CharacterDetailsApiModel>

typealias CharactersListResponse = ResponseApiModel<List<CharacterListApiModel>>