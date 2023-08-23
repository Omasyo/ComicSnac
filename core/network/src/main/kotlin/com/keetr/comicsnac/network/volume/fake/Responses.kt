package com.keetr.comicsnac.network.volume.fake

import com.keetr.comicsnac.network.volume.models.VolumeDetailsResponse
import java.io.File

internal const val root = "src/main/kotlin/com/keetr/comicsnac/network/volume/fake/"

internal val VolumesResponse = File(root, "VolumesResponse.json").readText()

internal val VolumeDetailsResponse = File(root, "VolumeDetailsResponse.json").readText()