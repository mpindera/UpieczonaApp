package com.example.upieczona

sealed class Destination(val route: String) {
    object HomePage : Destination("MainScreenUpieczona")
    object ContentPage : Destination("Content")
    object MainPageOfUpieczona : Destination("MainScreenOfUpieczonaToRefresh")
}