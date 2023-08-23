package com.example.upieczona.staticobjects

import com.example.upieczona.api.NetworkModule
import com.example.upieczona.viewmodels.UpieczonaViewModel

object ApiUtils {
    val apiUtil = UpieczonaViewModel(api = NetworkModule().api)
}