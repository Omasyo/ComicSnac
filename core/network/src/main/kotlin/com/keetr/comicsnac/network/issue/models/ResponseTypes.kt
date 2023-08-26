package com.keetr.comicsnac.network.issue.models

import com.keetr.comicsnac.network.common.models.ResponseApiModel
import com.keetr.comicsnac.network.search.models.IssueListApiModel

typealias IssueDetailsResponse = ResponseApiModel<IssueDetailsApiModel>

typealias IssueListResponse = ResponseApiModel<List<IssueListApiModel>>