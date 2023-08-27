package com.keetr.comicsnac.data.concept

import com.keetr.comicsnac.data.issue.toIssueBasic
import com.keetr.comicsnac.model.concept.Concept
import com.keetr.comicsnac.model.concept.ConceptBasic
import com.keetr.comicsnac.model.concept.ConceptDetails
import com.keetr.comicsnac.model.issue.IssueBasic
import com.keetr.comicsnac.network.common.models.ConceptApiModel
import com.keetr.comicsnac.network.common.models.ImageApiModel
import com.keetr.comicsnac.network.common.models.IssueApiModel
import com.keetr.comicsnac.network.concept.models.ConceptDetailsApiModel
import com.keetr.comicsnac.network.search.models.ConceptListApiModel

internal fun List<ConceptApiModel>.toBasic() = map { apiModel -> apiModel.toConceptBasic() }

internal fun ConceptApiModel.toConceptBasic() = ConceptBasic(
    apiDetailUrl = apiDetailUrl, id = id, name = name
)

internal fun List<ConceptListApiModel>.toConcepts() = map { apiModel -> apiModel.toConcept() }

internal fun ConceptListApiModel.toConcept() = Concept(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)

internal fun ConceptDetailsApiModel.toConceptDetails() =
    ConceptDetails(
        aliases = aliases?.split('\n') ?: emptyList(),
        apiDetailUrl = apiDetailUrl,
        deck = deck ?: "",
        description = description ?: "",
        firstAppearedInIssue = firstAppearedInIssue.toIssueBasic(),
        id = id,
        imageUrl = image.smallUrl,
        issuesId = issueCredits.map { it.id },
        name = name,
        siteDetailUrl = siteDetailUrl,
        volumesId = volumeCredits.map { it.id }
    )