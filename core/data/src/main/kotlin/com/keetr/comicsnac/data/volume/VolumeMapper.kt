package com.keetr.comicsnac.data.volume

import com.keetr.comicsnac.model.volume.VolumeBasic
import com.keetr.comicsnac.network.common.models.VolumeApiModel

fun VolumeApiModel.toBasic() = VolumeBasic(apiDetailUrl = apiDetailUrl, id = id, name = name)