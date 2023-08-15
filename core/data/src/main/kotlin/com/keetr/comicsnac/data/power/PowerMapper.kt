package com.keetr.comicsnac.data.power

import android.health.connect.datatypes.units.Power
import com.keetr.comicsnac.model.people.PersonBasic
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.network.common.models.CreatorApiModel
import com.keetr.comicsnac.network.common.models.PowerApiModel

fun List<PowerApiModel>.toBasic() = map { apiModel -> apiModel.toPowerBasic() }

fun PowerApiModel.toPowerBasic() = PowerBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)