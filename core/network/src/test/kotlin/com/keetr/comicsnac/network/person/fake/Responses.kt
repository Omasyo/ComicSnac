package com.keetr.comicsnac.network.person.fake

import java.io.File

internal const val root = "src/test/kotlin/com/keetr/comicsnac/network/person/fake/"

internal val PeopleResponse = File(root, "PeopleResponse.json").readText()

internal val PersonResponse = File(root, "PersonDetailsResponse.json").readText()