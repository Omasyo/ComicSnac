package com.keetr.comicsnac.network.character.fake

import java.io.File

internal const val root = "src/test/kotlin/com/keetr/comicsnac/network/character/fake/"

internal val BatmanDetailsResponse = File(root, "BatmanDetailsResponse.json").readText()

internal val CharactersResponse = File(root, "CharactersResponse.json").readText()

internal val RecentCharactersResponse = File(root, "RecentCharactersResponse.json").readText()

internal val MaleCharactersResponse = File(root, "MaleCharactersResponse.json").readText()

internal val FilteredCharactersResponse = File(root, "FilteredCharactersResponse.json").readText()