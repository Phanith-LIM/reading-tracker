package com.app.readingtracker.pages.sign_in

sealed class SignInRoute(val route: String) {
    data object SignInView: SignInRoute("sign_in")
    data object PageView: SignInRoute("/")
}