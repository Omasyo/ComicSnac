package com.keetr.comicsnac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keetr.comicsnac.data.settings.AuthRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var initialSchemeId by Delegates.notNull<Int>()

    var apiKeyPresent by Delegates.notNull<Boolean>()
        private set

    init {
        runBlocking {
            initialSchemeId = settingsRepository.getColorSchemeId().first()
            apiKeyPresent = !authRepository.getApiKey().firstOrNull().isNullOrBlank()
        }
    }

    val selectedSchemeId = settingsRepository.getColorSchemeId()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialSchemeId)
}