package com.example.upieczona

sealed class Destination(val route: String) {
    object HomePage : Destination("Home")
    object ContentPage : Destination("Content")
}