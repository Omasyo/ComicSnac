package com.keetr.comicsnac.network.origin.models

import com.keetr.comicsnac.network.common.models.OriginApiModel
import com.keetr.comicsnac.network.common.models.ResponseApiModel

typealias OriginDetailsResponse = ResponseApiModel<OriginDetailsApiModel>

typealias OriginListResponse = ResponseApiModel<List<OriginApiModel>>