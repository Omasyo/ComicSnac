package com.keetr.comicsnac.network.team.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.TeamListApiModel

typealias TeamDetailsResponse = ResponseApiModel<TeamDetailsApiModel>

typealias TeamListResponse = ResponseApiModel<List<TeamListApiModel>>