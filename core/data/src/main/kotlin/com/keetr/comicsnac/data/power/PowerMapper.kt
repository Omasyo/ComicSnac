package com.keetr.comicsnac.data.power

import android.health.connect.datatypes.units.Power
import com.keetr.comicsnac.model.people.PersonBasic
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.model.power.PowerDetails
import com.keetr.comicsnac.network.common.models.CreatorApiModel
import com.keetr.comicsnac.network.common.models.PowerApiModel
import com.keetr.comicsnac.network.power.models.PowerDetailsApiModel
import com.keetr.comicsnac.network.power.models.PowerListApiModel

internal fun List<PowerApiModel>.toBasic() = map { apiModel -> apiModel.toPowerBasic() }

internal fun PowerApiModel.toPowerBasic() = PowerBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun List<PowerListApiModel>.toPowers() = map { apiModel -> apiModel.toPower() }

internal fun PowerListApiModel.toPower() = PowerBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun PowerDetailsApiModel.toPowerDetails() =
    PowerDetails(
        apiDetailUrl = apiDetailUrl,
        characterIds = characters.map { it.id },
        description = description ?: "",
        id = id,
        name = name,
        siteDetailUrl = siteDetailUrl
    )