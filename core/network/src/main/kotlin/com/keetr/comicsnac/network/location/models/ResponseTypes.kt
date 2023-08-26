package com.keetr.comicsnac.network.location.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.`object`.models.ObjectDetailsApiModel
import com.keetr.comicsnac.network.`object`.models.ObjectListApiModel

typealias LocationDetailsResponse = ResponseApiModel<ObjectDetailsApiModel>

typealias LocationListResponse = ResponseApiModel<List<ObjectListApiModel>>