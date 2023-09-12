package com.keetr.comicsnac.categories.character

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    characterRepository: CharacterRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Character>(settingsRepository) {

    override val items =
        characterRepository.getAllCharacters(Gender.All).cachedIn(viewModelScope)

}

