package com.keetr.comicsnac.data.people

import com.keetr.comicsnac.model.people.PersonBasic
import com.keetr.comicsnac.model.people.PersonCredit
import com.keetr.comicsnac.network.common.models.CreatorApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel

fun List<CreatorApiModel>.toBasic() = map { apiModel -> apiModel.toPersonBasic() }

fun List<PersonCreditApiModel>.toCredits() = map { apiModel -> apiModel.toPersonCredit() }

fun CreatorApiModel.toPersonBasic() = PersonBasic(
    apiDetailUrl = apiDetailUrl.replace("creator", "person"), id = id, name = name
)

fun PersonCreditApiModel.toPersonCredit() = PersonCredit(
    apiDetailUrl = apiDetailUrl, id = id, name = name, role = role
)