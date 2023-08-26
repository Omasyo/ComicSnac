package com.keetr.comicsnac.network.publisher.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel

typealias PublisherDetailsResponse = ResponseApiModel<PublisherDetailsApiModel>

typealias PublisherCharactersResponse = ResponseApiModel<PublisherCharactersApiModel>

typealias PublisherVolumesResponse = ResponseApiModel<PublisherVolumesApiModel>

typealias PublisherListResponse = ResponseApiModel<List<PublisherListApiModel>>