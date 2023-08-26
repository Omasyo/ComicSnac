package com.keetr.comicsnac.network.`object`.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.ObjectListApiModel

typealias ObjectDetailsResponse = ResponseApiModel<ObjectDetailsApiModel>

typealias ObjectListResponse = ResponseApiModel<List<ObjectListApiModel>>