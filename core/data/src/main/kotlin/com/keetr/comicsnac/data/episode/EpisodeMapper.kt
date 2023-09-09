package com.keetr.comicsnac.data.episode

import com.keetr.comicsnac.model.episode.EpisodeBasic
import com.keetr.comicsnac.network.common.models.EpisodeApiModel

fun EpisodeApiModel.toEpisodeBasic() =
    EpisodeBasic(apiDetailUrl = apiDetailUrl, id = id, name = name)