package com.keetr.comicsnac.data.series

import androidx.paging.PagingData
import com.keetr.comicsnac.data.RepositoryResponse
import com.keetr.comicsnac.model.movie.Movie
import com.keetr.comicsnac.model.movie.MovieDetails
import com.keetr.comicsnac.model.`object`.ObjectDetails
import com.keetr.comicsnac.model.`object`.ObjectItem
import com.keetr.comicsnac.model.series.Series
import com.keetr.comicsnac.model.series.SeriesDetails
import com.keetr.comicsnac.network.movie.models.MovieListApiModel
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    fun getMovieDetails(id: String): Flow<RepositoryResponse<SeriesDetails>>

    fun getRecentSeries(): Flow<RepositoryResponse<List<Series>>>

    fun getAllSeries(): Flow<PagingData<Series>>

    fun getSeriesWithId(seriesId: List<Int>): Flow<PagingData<Series>>
}