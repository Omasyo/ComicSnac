package com.keetr.comicsnac.network.publisher.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.PublisherListApiModel

typealias PublisherDetailsResponse = ResponseApiModel<PublisherDetailsApiModel>

typealias PublisherCharactersResponse = ResponseApiModel<PublisherCharactersApiModel>

typealias PublisherVolumesResponse = ResponseApiModel<PublisherVolumesApiModel>

typealias PublisherListResponse = ResponseApiModel<List<PublisherListApiModel>>