package com.example.upieczona.destination

sealed class Destination(val route: String) {
    object HomePageOfUpieczona : Destination("MainScreenUpieczona")
    object ContentPageOfUpieczona : Destination("Content/{postIndex}")
    object MainPageOfUpieczona : Destination("MainScreenOfUpieczonaToRefresh")
    object FavoritePageOfUpieczona : Destination("FavoritePage")
<<<<<<< HEAD
    object FilterPageOfUpieczona : Destination("FilterPage/{tagItems}")

=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
}