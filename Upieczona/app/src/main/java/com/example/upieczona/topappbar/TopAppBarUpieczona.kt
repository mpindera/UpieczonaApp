package com.example.upieczona.topappbar

<<<<<<< HEAD
import android.app.Activity
import android.util.Log
=======
import androidx.compose.foundation.background
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
<<<<<<< HEAD
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
=======
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.NavigationAppHost
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import com.example.upieczona.R
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun TopAppBarUpieczona(
<<<<<<< HEAD
  onUpieczonaClick: () -> Unit,
  onSearchIconClick: () -> Unit,
  navController: NavHostController,
  mainViewModel: MainViewModel,
  pageInfo: MainPageState
=======
    onUpieczonaClick: () -> Unit,
    navController: NavHostController
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
) {
  var shineAlpha by remember { mutableStateOf(1f) }
  val searchWidgetState by mainViewModel.searchWidgetState

<<<<<<< HEAD
  LaunchedEffect(Unit) {
    val waitDuration = 100L
    delay(waitDuration)
    shineAlpha = 1f
  }

  when (searchWidgetState) {
    WidgetState.CLOSED -> {
      TopBarDefault(
        onClickUpieczona = onUpieczonaClick,
        onSearchIconClick = { mainViewModel.updateSearchWidgetState(newValue = WidgetState.OPEN) },
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
=======
    TopBar(
        onClickUpieczona = onUpieczonaClick,
        navController = navController
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
    )
  )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
<<<<<<< HEAD
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
    SearchIconButton(onSearchIconClick = onSearchIconClick, pageInfo = pageInfo)
  })

}

@Composable
fun SearchIconButton(
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
=======
fun TopBar(
    onClickUpieczona: () -> Unit,
    navController: NavHostController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .clickable {
                        onClickUpieczona()
                    }
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialsUtils.colorPinkMain
        ),
        navigationIcon = {
            IconButton(onClick = {
                if (navController.currentBackStackEntry?.destination?.route != "MainScreenOfUpieczonaToRefresh") {
                    navController.navigateUp()
                }
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {

            }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopAppBarUpieczonaPreview() {
    val navController = rememberNavController()
    TopBar(onClickUpieczona = {}, navController = navController)
    Divider(modifier = Modifier.height(2.dp).background(Color.Black))

}
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
