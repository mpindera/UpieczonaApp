package com.example.upieczona.destination

sealed class Destination(val route: String) {
    object HomePageOfUpieczona : Destination("MainScreenUpieczona")
    object ContentPageOfUpieczona : Destination("Content/{postIndex}")
    object MainPageOfUpieczona : Destination("MainScreenOfUpieczonaToRefresh")
    object FavoritePageOfUpieczona : Destination("FavoritePage")
    object FilterPageOfUpieczona : Destination("FilterPage/{tagItems}")

}