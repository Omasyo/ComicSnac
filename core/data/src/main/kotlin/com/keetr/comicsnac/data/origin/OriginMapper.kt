package com.keetr.comicsnac.data.origin

import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.network.common.models.OriginApiModel

fun OriginApiModel.toOriginBasic() = OriginBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name

)