package com.keetr.comicsnac.network.storyarc.fake

import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/storyarc/fake/"

internal val StoryArcDetailsResponse = File(root, "StoryArcDetailsResponses.json").readText()

internal val StoryArcsResponse = File(root, "StoryArcsResponse.json").readText()