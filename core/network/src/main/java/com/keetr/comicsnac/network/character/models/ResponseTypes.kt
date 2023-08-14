package com.keetr.comicsnac.network.character.models

import com.keetr.comicsnac.network.common.ResponseApiModel
import com.keetr.comicsnac.network.character.models.CharacterApiModel
import com.keetr.comicsnac.network.character.models.CharacterDetailsApiModel

typealias CharacterDetailsResponse = ResponseApiModel<CharacterDetailsApiModel>

typealias CharactersListResponse = ResponseApiModel<CharacterApiModel>