package com.keetr.comicsnac.network.storyarc.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.StoryArcListApiModel

typealias StoryArcDetailsResponse = ResponseApiModel<StoryArcDetailsApiModel>

typealias StoryArcListResponse = ResponseApiModel<List<StoryArcListApiModel>>