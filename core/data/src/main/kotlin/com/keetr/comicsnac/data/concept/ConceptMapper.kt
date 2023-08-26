package com.keetr.comicsnac.data.concept

import com.keetr.comicsnac.model.concept.ConceptBasic
import com.keetr.comicsnac.network.common.models.ConceptApiModel

internal fun List<ConceptApiModel>.toBasic() = map { apiModel -> apiModel.toConceptBasic() }

internal fun ConceptApiModel.toConceptBasic() = ConceptBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)