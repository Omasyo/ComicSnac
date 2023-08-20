package com.keetr.comicsnac.data.people

import com.keetr.comicsnac.model.people.PersonBasic
import com.keetr.comicsnac.model.people.PersonCredit
import com.keetr.comicsnac.network.common.models.CreatorApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel

internal fun List<CreatorApiModel>.toBasic() = map { apiModel -> apiModel.toPersonBasic() }

internal fun List<PersonCreditApiModel>.toCredits() = map { apiModel -> apiModel.toPersonCredit() }

internal fun CreatorApiModel.toPersonBasic() = PersonBasic(
    apiDetailUrl = apiDetailUrl.replace("creator", "person"), id = id, name = name
)

internal fun PersonCreditApiModel.toPersonCredit() = PersonCredit(
    apiDetailUrl = apiDetailUrl, id = id, name = name, role = role
)