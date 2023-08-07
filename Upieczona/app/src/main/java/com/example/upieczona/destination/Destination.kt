package com.example.upieczona.destination

sealed class Destination(val route: String) {
    object HomePage : Destination("MainScreenUpieczona")
    object ContentPage : Destination("Content/{postIndex}")
    object MainPageOfUpieczona : Destination("MainScreenOfUpieczonaToRefresh")
}