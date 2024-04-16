package com.app.readingtracker.pages

sealed class PageRoute(val route: String) {
    data object HomeView: PageRoute("home")
    data object BookView: PageRoute("book")
    data object ChartView: PageRoute("chart")
    data object ProfileView: PageRoute("profile")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}