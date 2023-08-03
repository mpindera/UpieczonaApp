package com.example.upieczona.viewmodels

import android.os.Looper
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class ViewModelUpieczonaTopAndBottomBar : ViewModel() {
    var shineAlpha by mutableStateOf(1f)
    val tapGesture = Modifier.pointerInput(Unit) {
        detectTapGestures { tapOffset ->
            shineAlpha = 0.5f
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                shineAlpha = 1f
            }, 100)
        }
    }
}
