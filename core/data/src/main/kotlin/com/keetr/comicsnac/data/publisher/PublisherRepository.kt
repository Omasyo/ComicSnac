package com.keetr.comicsnac.data.publisher

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.publisher.Publisher
import com.keetr.comicsnac.model.publisher.PublisherDetails
import kotlinx.coroutines.flow.Flow

interface PublisherRepository {
    fun getPublisherDetails(id: String): Flow<RepositoryResponse<PublisherDetails>>

    fun getPublisherCharactersId(id: String): Flow<RepositoryResponse<List<Int>>>

    fun getPublisherVolumesId(id: String): Flow<RepositoryResponse<List<Int>>>

    fun getAllPublishers(): Flow<PagingData<Publisher>>
}