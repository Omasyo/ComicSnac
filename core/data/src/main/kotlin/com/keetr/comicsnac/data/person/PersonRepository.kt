package com.keetr.comicsnac.data.person

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.Sort
import com.keetr.comicsnac.model.person.Person
import com.keetr.comicsnac.model.person.PersonDetails
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersonDetails(id: String): Flow<RepositoryResponse<PersonDetails>>

    fun getAllPeople(sort: Sort = Sort.Descending): Flow<PagingData<Person>>
}