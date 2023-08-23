package com.keetr.comicsnac.network.teams.fake

import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/teams/fake/"

internal val TeamsResponse = File(root, "TeamsResponse.json").readText()

internal val NewMutantsResponse = File(root, "NewMutantsResponse.json").readText()