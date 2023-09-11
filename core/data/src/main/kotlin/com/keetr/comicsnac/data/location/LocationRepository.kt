package com.keetr.comicsnac.data.location

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.location.Location
import com.keetr.comicsnac.model.location.LocationDetails
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocationDetails(id: String): Flow<RepositoryResponse<LocationDetails>>

    fun getAllLocations(): Flow<PagingData<Location>>

    fun getLocationsWithId(locationsId: List<Int>): Flow<PagingData<Location>>
}