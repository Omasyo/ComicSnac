package com.keetr.comicsnac.model

abstract class NavigationRoute(val format: String) {
    protected open val requiredArguments: List<String> = listOf()
    protected open val optionalArguments: List<String> = listOf()

    open val route: String
        get() = format.format(*(
                requiredArguments + optionalArguments
                ).map { "{$it}" }.toTypedArray())

    open fun route(vararg args: Any) = format.format(*args)
}