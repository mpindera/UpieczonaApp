package com.example.upieczona.contentview

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.upieczona.destination.Destination
import com.example.upieczona.mainscreen.MainPageOfUpieczonaWithRecipes
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.ApiUtils
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.topappbar.TopAppBarUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentViewUpieczona(
  postIndex: Int?, upieczonaViewModel: UpieczonaViewModel, navController: NavHostController
) {
  var mainPageState by remember {
    mutableStateOf(MainPageState.Default)
  }

  LaunchedEffect(mainPageState) {
    if (mainPageState == MainPageState.UpieczonaClicked) {
      navController.navigate(Destination.MainPageOfUpieczona.route)
    }
  }

  val postDetails = remember(upieczonaViewModel.allPosts) {
    postIndex?.let { index ->
      upieczonaViewModel.allPosts.value.find { it.id == index }
    }
  }

  Scaffold(
    topBar = {
      if (mainPageState == MainPageState.Default) {
        TopAppBarUpieczona(onUpieczonaClick = {
          mainPageState = MainPageState.UpieczonaClicked
        }, navController = navController)
      }
    },
    content = { padding ->
      Column(
        modifier = Modifier.padding(padding)
      ) {
        if (postDetails != null) {
          val urlsListPhotosUpieczona = remember(postDetails) {
            upieczonaViewModel.extractPhotosUrls(postDetails.content.rendered)
          }

          val ingredientTitleUpieczona = remember(postDetails) {
            upieczonaViewModel.getCachedIngredients(postDetails.content.rendered)
          }


          val extractShopListUpieczona = remember(postDetails) {
            upieczonaViewModel.extractShopList(postDetails.content.rendered)
          }

          val decodedTextFromTitleUpieczona = remember(postDetails) {
            HtmlCompat.fromHtml(
              postDetails.title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
          }

          @Composable
          fun FetchTitleWhenOneTitle() {
            Text(
              modifier = Modifier.padding(5.dp),
              fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
              text = ingredientTitleUpieczona[0],
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            extractShopListUpieczona.forEach { item ->
              val checkedState = remember { mutableStateOf(false) }
              Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                  checked = checkedState.value,
                  onCheckedChange = {
                    checkedState.value = it
                  }
                )
                if (checkedState.value) {
                  Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                    text = item,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    textDecoration = TextDecoration.LineThrough
                  )
                }else{
                  Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                    text = item,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                  )
                }
              }
            }
          }

          @Composable
          fun FetchTitleWhenTwoTitle() {
            Text(
              modifier = Modifier.padding(5.dp),
              fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
              text = ingredientTitleUpieczona[0],
              fontSize = 20.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
            if (upieczonaViewModel.ingredientsLists(postDetails.content.rendered)
                .isNotEmpty()
            ) {
              val firstIngredients =
                Regex("<p class=\"ingredient-item-name is-strikethrough-active\">(.*?)</p>")
                  .findAll(upieczonaViewModel.ingredientsLists(postDetails.content.rendered)[0].groupValues[1])
                  .map { it.groupValues[1] }
                  .toList()
              Text(
                modifier = Modifier.padding(8.dp),
                fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                text = firstIngredients.toString(),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
              )


              Text(
                modifier = Modifier.padding(5.dp),
                fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                text = ingredientTitleUpieczona[1],
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
              )

              if (upieczonaViewModel.ingredientsLists(postDetails.content.rendered).size > 1) {
                val secondIngredients =
                  Regex("<p class=\"ingredient-item-name is-strikethrough-active\">(.*?)</p>")
                    .findAll(upieczonaViewModel.ingredientsLists(postDetails.content.rendered)[1].groupValues[1])
                    .map { it.groupValues[1] }
                    .toList()



                Text(
                  modifier = Modifier.padding(8.dp),
                  fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
                  text = secondIngredients.toString(),
                  fontSize = 14.sp,
                  textAlign = TextAlign.Center
                )
              }
            }
          }

          @Composable
          fun FetchTitle() {
            when (ingredientTitleUpieczona.size) {
              1 -> FetchTitleWhenOneTitle()
              2 -> FetchTitleWhenTwoTitle()
            }
          }

          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
          ) {
            item {
              Divider()

              ImagePager(
                urlsListPhotosUpieczona.size,
                urlsListPhotosUpieczona
              )

              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
              ) {
                Text(
                  modifier = Modifier.padding(5.dp),
                  fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                  text = decodedTextFromTitleUpieczona,
                  fontSize = 25.sp,
                  textAlign = TextAlign.Center
                )
                Column(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                  horizontalAlignment = Alignment.Start,
                  verticalArrangement = Arrangement.Center
                ) {
                  FetchTitle()
                }
              }
            }
          }
        } else {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "Post not found",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            navController.navigateUp()
          }
        }
      }
    },
  )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePager(imageUrlsSize: Int, imageUrls: List<String>) {
  val pageState = rememberPagerState()

  HorizontalPager(
    pageCount = imageUrls.size,
    state = pageState
  ) { pageIndex ->
    val imageUrl = imageUrls[pageIndex]
    Row(
      modifier = Modifier
        .height(400.dp)
        .width(400.dp)
        .background(Color.White),
      horizontalArrangement = Arrangement.Center
    ) {
      AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxSize()
      )
    }
  }

  Divider()

  Row(
    modifier = Modifier
      .height(20.dp)
      .fillMaxSize(),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.Center
  ) {
    repeat(imageUrlsSize) { iteration ->
      val color =
        if (pageState.currentPage == iteration) Color.DarkGray else Color.LightGray
      Box(
        modifier = Modifier
          .padding(2.dp)
          .clip(CircleShape)
          .background(color = color)
          .size(7.dp)
      )
    }
  }

}
