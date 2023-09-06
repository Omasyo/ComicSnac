package com.keetr.comicsnac.categories.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.model.LayoutType
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val characters: Flow<PagingData<Character>> =
        characterRepository.getAllCharacters(Gender.All).cachedIn(viewModelScope)


    val layoutType = settingsRepository.getLayoutPreference()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LayoutType.Grid)

    fun onToggleLayout() {
        viewModelScope.launch {
            settingsRepository.updateLayoutPreference(
                if (layoutType.first() == LayoutType.Grid) LayoutType.List else LayoutType.Grid
            )
        }
    }
}

