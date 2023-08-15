package com.keetr.comicsnac.network.issue.fake

import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/issue/fake/"

internal val AmazingSpidermanIssueResponse = File(root, "AmazingSpiderman#698.json").readText()

internal val IssuesResponse = File(root, "IssuesResponse.json").readText()

internal val RecentIssuesResponse = File(root, "RecentIssuesResponse.json").readText()