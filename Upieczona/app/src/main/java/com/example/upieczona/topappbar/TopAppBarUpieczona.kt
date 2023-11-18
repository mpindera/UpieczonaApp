package com.example.upieczona.topappbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.upieczona.R
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.viewmodels.UpieczonaMainViewModel
import kotlinx.coroutines.delay

@Composable
fun TopAppBarUpieczona(
  onUpieczonaClick: () -> Unit,
  onSearchIconClick: () -> Unit,
  navController: NavHostController,
  upieczonaMainViewModel: UpieczonaMainViewModel,
  pageInfo: MainPageState
) {
  var shineAlpha by remember { mutableStateOf(1f) }
  val searchWidgetState by upieczonaMainViewModel.searchWidgetState

  LaunchedEffect(Unit) {
    val waitDuration = 100L
    delay(waitDuration)
    shineAlpha = 1f
  }

  when (searchWidgetState) {
    WidgetState.CLOSED -> {
      TopBarDefault(
        onClickUpieczona = onUpieczonaClick,
        onSearchIconClick = { upieczonaMainViewModel.updateSearchWidgetState(newValue = WidgetState.OPEN) },
        onBackIconClick = {},
        navController = navController,
        pageInfo = pageInfo
      )
    }
    WidgetState.OPEN -> {
      TopBarSearch(
        onSearchIconClick = {},
        onClearIconClick = {},
        navController = navController,
        pageInfo = pageInfo
      )
    }
  }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarSearch(
  onSearchIconClick: () -> Unit,
  onClearIconClick: () -> Unit,
  navController: NavHostController,
  pageInfo: MainPageState
) {
  TopAppBar(
    title = {
      TextField(value = "", onValueChange = {

      }, colors = TextFieldDefaults.textFieldColors(
        containerColor = MaterialsUtils.colorPinkMain
      ), modifier = Modifier.fillMaxSize(),
        trailingIcon = {
          ClearIconButton(onClearIconClick = onClearIconClick)
        },
        leadingIcon = {
          SearchButton(onSearchIconClick = onSearchIconClick, pageInfo = pageInfo)
        }
      )
    }, colors = TopAppBarDefaults.smallTopAppBarColors(
      containerColor = MaterialsUtils.colorPinkMain
    )
  )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarDefault(
  onClickUpieczona: () -> Unit,
  onSearchIconClick: () -> Unit,
  onBackIconClick: () -> Unit,
  navController: NavHostController,
  pageInfo: MainPageState,
) {
  TopAppBar(title = {
    Text(text = stringResource(id = R.string.app_name),
      fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
      modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .clickable {
          onClickUpieczona()
        })
  }, colors = TopAppBarDefaults.smallTopAppBarColors(
    containerColor = MaterialsUtils.colorPinkMain
  ), navigationIcon = {
    BackIconButton(
      navController = navController, onBackIconClick = onBackIconClick, pageInfo = pageInfo
    )
  }, actions = {

  })

}

@Composable
fun SearchButton(
  onSearchIconClick: () -> Unit, pageInfo: MainPageState
) {
  IconButton(onClick = {
    onSearchIconClick()
  }) {
    if (pageInfo == MainPageState.UPIECZONA_CLICKED) {
      Icon(Icons.Default.Search, contentDescription = null)
    }
  }
}

@Composable
fun ClearIconButton(
  onClearIconClick: () -> Unit
) {
  IconButton(onClick = {
    onClearIconClick()
  }) {
    Icon(Icons.Default.Clear, contentDescription = null)
  }
}

@Composable
fun BackIconButton(
  navController: NavHostController, onBackIconClick: () -> Unit, pageInfo: MainPageState
) {
  IconButton(onClick = {
    when (pageInfo) {
      MainPageState.FAVORITE, MainPageState.FILTER_PAGE, MainPageState.CONTENT_VIEW -> {
        navController.navigateUp()
      }
      MainPageState.DEFAULT -> {

      }
      MainPageState.UPIECZONA_CLICKED -> {
        Modifier.clickable(enabled = false) {}
      }
    }
    onBackIconClick()
  }) {
    if (pageInfo != MainPageState.UPIECZONA_CLICKED) {
      Icon(
        Icons.Default.ArrowBack,
        contentDescription = null
      )
    }
  }
}
