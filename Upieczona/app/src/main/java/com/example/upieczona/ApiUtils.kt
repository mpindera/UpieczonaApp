package com.example.upieczona

import com.example.http.NetworkModule
import com.example.upieczona.viewmodels.UpieczonaViewModel

object ApiUtils {
    val apiUtil = UpieczonaViewModel(api = NetworkModule().api)
}