package com.keetr.comicsnac.network.volume.fake

import java.io.File

internal const val root = "src/test/kotlin/com/keetr/comicsnac/network/volume/fake/"

internal val VolumesResponse = File(root, "VolumesResponse.json").readText()

internal val VolumeDetailsResponse = File(root, "VolumeDetailsResponse.json").readText()