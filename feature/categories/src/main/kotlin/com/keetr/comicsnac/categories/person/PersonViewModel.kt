package com.keetr.comicsnac.categories.person

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.keetr.comicsnac.categories.CategoryViewModel
import com.keetr.comicsnac.data.character.CharacterRepository
import com.keetr.comicsnac.data.settings.SettingsRepository
import com.keetr.comicsnac.data.person.PersonRepository
import com.keetr.comicsnac.model.character.Character
import com.keetr.comicsnac.model.other.Gender
import com.keetr.comicsnac.model.person.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class PersonViewModel @Inject constructor(
    personRepository: PersonRepository,
    settingsRepository: SettingsRepository
) : CategoryViewModel<Person>(settingsRepository) {

    override val items =
        personRepository.getAllPeople().cachedIn(viewModelScope)

}

