package com.example.upieczona.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.topappbar.WidgetState

class MainViewModel : ViewModel() {
  private val _searchWidgetState: MutableState<WidgetState> =
    mutableStateOf(value = WidgetState.CLOSED)
  val searchWidgetState: State<WidgetState> = _searchWidgetState

  fun updateSearchWidgetState(newValue: WidgetState) {
    _searchWidgetState.value = newValue
  }

  private val _pageState: MutableState<MainPageState> =
    mutableStateOf(value = MainPageState.DEFAULT)
  var pageState: State<MainPageState> = _pageState

  fun updatePageState(newValue: MainPageState): MainPageState {
    _pageState.value = newValue
    return newValue
  }

}