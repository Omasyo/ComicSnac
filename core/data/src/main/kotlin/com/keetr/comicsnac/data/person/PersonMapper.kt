package com.keetr.comicsnac.data.person

import com.keetr.comicsnac.model.person.Person
import com.keetr.comicsnac.model.person.PersonBasic
import com.keetr.comicsnac.model.person.PersonCredit
import com.keetr.comicsnac.network.common.models.PersonApiModel
import com.keetr.comicsnac.network.common.models.PersonCreditApiModel
import com.keetr.comicsnac.network.search.models.PersonListApiModel

internal fun List<PersonApiModel>.toBasic() = map { apiModel -> apiModel.toPersonBasic() }

internal fun List<PersonCreditApiModel>.toCredits() = map { apiModel -> apiModel.toPersonCredit() }

internal fun PersonApiModel.toPersonBasic() = PersonBasic(
    apiDetailUrl = apiDetailUrl.replace("creator", "person"), id = id, name = name
)

internal fun PersonCreditApiModel.toPersonCredit() = PersonCredit(
    apiDetailUrl = apiDetailUrl, id = id, name = name, role = role
)

internal fun PersonListApiModel.toPerson() = Person(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)