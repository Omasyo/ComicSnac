package com.keetr.comicsnac.details

import androidx.navigation.navDeepLink
import com.keetr.comicsnac.model.NavigationRoute


const val Domain = "https://comicvine.gamespot.com"
const val ApiBaseUrl = "${Domain}/api/"

const val Arg = "id"

abstract class DetailsNavigationRoute(path: String, private val categoryId: String = "") :
    NavigationRoute("$path/$categoryId-%s/") {

    private val apiDeepLinkPattern get() = "$ApiBaseUrl$route"

    protected val webDeepLinkPattern get() = "$Domain/{_}/$categoryId-{${requiredArguments.first()}}/"

    open val deepLinks
        get() = listOf(
            navDeepLink {
                uriPattern = apiDeepLinkPattern
            },
            navDeepLink {
                uriPattern = webDeepLinkPattern
            }
        )
}