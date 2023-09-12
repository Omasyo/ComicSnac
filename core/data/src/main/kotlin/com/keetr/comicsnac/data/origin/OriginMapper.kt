package com.keetr.comicsnac.data.origin

import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.model.origin.OriginDetails
import com.keetr.comicsnac.network.common.models.OriginApiModel
import com.keetr.comicsnac.network.origin.models.OriginDetailsApiModel

internal fun List<OriginApiModel>.toOrigins() = map { apiModel -> apiModel.toOriginBasic() }

internal fun OriginApiModel.toOriginBasic() = OriginBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun OriginDetailsApiModel.toOriginDetails() =
    OriginDetails(
        apiDetailUrl = apiDetailUrl,
        charactersId = characters.map { it.id },
        id = id,
        name = name,
        siteDetailUrl = siteDetailUrl
    )