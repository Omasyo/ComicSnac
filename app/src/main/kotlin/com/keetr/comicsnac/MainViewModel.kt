package com.keetr.comicsnac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val selectedSchemeId = settingsRepository.getColorSchemeId()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)
}