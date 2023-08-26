package com.keetr.comicsnac.data.volume

import com.keetr.comicsnac.data.issue.toIssueBasic
import com.keetr.comicsnac.data.publisher.toPublisherBasic
import com.keetr.comicsnac.model.volume.Volume
import com.keetr.comicsnac.model.volume.VolumeBasic
import com.keetr.comicsnac.model.volume.VolumeDetails
import com.keetr.comicsnac.network.common.models.VolumeApiModel
import com.keetr.comicsnac.network.volume.models.VolumeDetailsApiModel
import com.keetr.comicsnac.network.volume.models.VolumeListApiModel

internal fun VolumeApiModel.toBasic() = VolumeBasic(
    apiDetailUrl = apiDetailUrl,
    id = id,
    name = name
)

internal fun List<VolumeListApiModel>.toVolumes() = map { apiModel -> apiModel.toVolume() }

internal fun VolumeListApiModel.toVolume() = Volume(
    apiDetailUrl = apiDetailUrl,
    deck = deck ?: "",
    id = id,
    imageUrl = image.smallUrl,
    name = name
)

internal fun VolumeDetailsApiModel.toVolumeDetails() =
    VolumeDetails(
        apiDetailUrl = apiDetailUrl,
        countOfIssues = countOfIssues,
        deck = deck ?: "",
        description = description ?: "",
        firstIssue = firstIssue?.toIssueBasic(),
        id = id,
        imageUrl = image.smallUrl,
        issuesId = issues.map { it.id },
        lastIssue = lastIssue?.toIssueBasic(),
        name = name,
        publisher = publisher?.toPublisherBasic(),
        siteDetailUrl = siteDetailUrl,
        startYear = startYear ?: ""
    )