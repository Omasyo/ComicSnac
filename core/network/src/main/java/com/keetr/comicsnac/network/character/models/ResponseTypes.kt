package com.keetr.comicsnac.network.character.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel

typealias CharacterDetailsResponse = ResponseApiModel<CharacterDetailsApiModel>

typealias CharactersListResponse = ResponseApiModel<List<CharacterApiModel>>