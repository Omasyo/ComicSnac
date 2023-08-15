package com.keetr.comicsnac.data.concept

import com.keetr.comicsnac.model.concept.ConceptBasic
import com.keetr.comicsnac.network.common.models.ConceptApiModel
import com.keetr.comicsnac.network.concept.models.ConceptListApiModel

fun List<ConceptApiModel>.toBasic() = map { apiModel -> apiModel.toConceptBasic() }

fun ConceptApiModel.toConceptBasic() = ConceptBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)