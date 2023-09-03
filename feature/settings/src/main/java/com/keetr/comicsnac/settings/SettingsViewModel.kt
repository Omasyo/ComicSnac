package com.keetr.comicsnac.settings

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.ui.theme.DefaultScheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val selectedSchemeId = settingsRepository.getColorSchemeId()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun onClickScheme(id: Int) {
        viewModelScope.launch {
            settingsRepository.updateColorSchemeId(id)
        }
    }
}