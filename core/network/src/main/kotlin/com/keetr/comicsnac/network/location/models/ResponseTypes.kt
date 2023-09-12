package com.keetr.comicsnac.network.location.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.LocationListApiModel

typealias LocationDetailsResponse = ResponseApiModel<LocationDetailsApiModel>

typealias LocationListResponse = ResponseApiModel<List<LocationListApiModel>>