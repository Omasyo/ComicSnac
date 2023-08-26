package com.keetr.comicsnac.network.concept.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.ConceptListApiModel

typealias ConceptDetailsResponse = ResponseApiModel<ConceptDetailsApiModel>

typealias ConceptListResponse = ResponseApiModel<List<ConceptListApiModel>>