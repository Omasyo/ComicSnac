package com.keetr.comicsnac.data.people

import com.keetr.comicsnac.model.person.PersonBasic
import com.keetr.comicsnac.model.person.PersonCredit
import com.keetr.comicsnac.network.common.models.PersonApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel

internal fun List<PersonApiModel>.toBasic() = map { apiModel -> apiModel.toPersonBasic() }

internal fun List<PersonCreditApiModel>.toCredits() = map { apiModel -> apiModel.toPersonCredit() }

internal fun PersonApiModel.toPersonBasic() = PersonBasic(
    apiDetailUrl = apiDetailUrl.replace("creator", "person"), id = id, name = name
)

internal fun PersonCreditApiModel.toPersonCredit() = PersonCredit(
    apiDetailUrl = apiDetailUrl, id = id, name = name, role = role
)