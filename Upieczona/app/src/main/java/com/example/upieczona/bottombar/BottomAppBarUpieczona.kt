package com.example.upieczona.bottombar

<<<<<<< HEAD
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
=======
import androidx.compose.foundation.layout.*
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
<<<<<<< HEAD
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.R
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun BottomAppBarUpieczona(navController: NavController, upieczonaViewModel: UpieczonaViewModel) {
  BottomBar(navController, upieczonaViewModel)
}

@Composable
fun BottomBar(navController: NavController, upieczonaViewModel: UpieczonaViewModel) {
  val context = LocalContext.current
  var dialogOpen by remember { mutableStateOf(false) }
  var selectedItemsMap by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
  val categoriesState by upieczonaViewModel.tagsUpieczona.collectAsState()
=======
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.R

@Composable
fun BottomAppBarUpieczona(navController: NavController) {
  BottomBar(navController)
}

@Composable
fun BottomBar(navController: NavController) {
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
  BottomAppBar(
    modifier = Modifier.height(75.dp), actions = {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
<<<<<<< HEAD
        Row(modifier = Modifier.padding(10.dp)) {
          FloatingActionButton(shape = RoundedCornerShape(100),
=======
        Row() {
          FloatingActionButton(
            shape = RoundedCornerShape(100),
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
            containerColor = MaterialsUtils.colorPink,
            onClick = {
              navController.navigate("FavoritePage")
            }) {
            Icon(
              imageVector = Icons.Default.Favorite,
              contentDescription = null,
              tint = MaterialsUtils.colorRed
            )
          }
<<<<<<< HEAD
          FloatingActionButton(onClick = {
            dialogOpen = true
          }) {
=======
          FloatingActionButton(
            onClick = {

            }) {
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
            Icon(
              painter = painterResource(id = R.drawable.baseline_filter_alt_24),
              contentDescription = null
            )
          }
        }
      }
    }, containerColor = MaterialsUtils.colorPinkMain
  )
<<<<<<< HEAD
  if (dialogOpen) {
    Dialog(
      onDismissRequest = {
        dialogOpen = false
      }, properties = DialogProperties(dismissOnClickOutside = false)
    ) {
      fetchTags(upieczonaViewModel)
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .height(470.dp),
        shape = RoundedCornerShape(10.dp),
      ) {
        Box {
          Box(modifier = Modifier.align(Alignment.TopEnd)) {
            IconButton(onClick = {
              selectedItemsMap.clear()
              dialogOpen = false
            }) {
              Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
          }

          Box(modifier = Modifier.align(Alignment.TopCenter)) {
            Text(text = stringResource(id = R.string.filters))
          }

          Box(
            modifier = Modifier
              .align(Alignment.CenterStart)
              .padding(20.dp)
          ) {
            LazyColumn {
              items(categoriesState.size) { item ->
                var isChecked by remember { mutableStateOf(selectedItemsMap.containsKey(item)) }
                val categoryId = categoriesState[item].id

                Box(modifier = Modifier.clickable {
                  isChecked = !isChecked
                  if (isChecked) {
                    selectedItemsMap[item] = categoryId
                  } else {
                    selectedItemsMap.remove(item)
                  }
                }) {
                  Row {
                    Checkbox(
                      checked = isChecked,
                      modifier = Modifier.padding(10.dp),
                      onCheckedChange = {
                        isChecked = it
                        if (it) {
                          selectedItemsMap[item] = categoryId
                        } else {
                          selectedItemsMap.remove(item)
                        }
                      })
                    Text(
                      text = categoriesState[item].name, modifier = Modifier.padding(top = 23.dp)
                    )
                  }
                }
              }
            }
          }
          Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            ElevatedButton(modifier = Modifier.padding(3.dp),
              border = BorderStroke(1.dp, Color.Black),
              onClick = {
                if(selectedItemsMap.isNotEmpty()){
                  upieczonaViewModel.navigateToTagsPage(selectedItemsMap, navController)
                  dialogOpen = false
                  selectedItemsMap.clear()
                }else{
                  Toast.makeText(context,"Musisz wbyrać Filtr",Toast.LENGTH_SHORT).show()
                }
              }) {
              Text(text = stringResource(id = R.string.filter))
            }
          }
        }
      }
    }
  }
}

fun fetchTags(upieczonaViewModel: UpieczonaViewModel) {
  upieczonaViewModel.fetchTags()
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarUpieczonaPreview() {
  val navController = rememberNavController()
<<<<<<< HEAD
  BottomAppBarUpieczona(navController, ApiUtils.apiUtil)
=======
  BottomAppBarUpieczona(navController)
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
}