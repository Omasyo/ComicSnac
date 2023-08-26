package com.keetr.comicsnac.network.team.fake

import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/team/fake/"

internal val TeamsResponse = File(root, "TeamsResponse.json").readText()

internal val NewMutantsResponse = File(root, "NewMutantsResponse.json").readText()