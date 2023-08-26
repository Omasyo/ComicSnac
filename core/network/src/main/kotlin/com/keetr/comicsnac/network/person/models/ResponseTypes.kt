package com.keetr.comicsnac.network.person.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.PersonListApiModel

typealias PersonDetailsResponse = ResponseApiModel<PersonDetailsApiModel>

typealias PersonListResponse = ResponseApiModel<List<PersonListApiModel>>