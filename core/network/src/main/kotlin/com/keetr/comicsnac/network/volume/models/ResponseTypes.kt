package com.keetr.comicsnac.network.volume.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.VolumeListApiModel

typealias VolumeDetailsResponse = ResponseApiModel<VolumeDetailsApiModel>

typealias VolumeListResponse = ResponseApiModel<List<VolumeListApiModel>>