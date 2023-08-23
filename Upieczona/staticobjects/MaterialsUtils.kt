package com.example.upieczona.staticobjects

import android.app.ActionBar
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upieczona.R
import com.example.upieczona.bottombar.BottomAppBarUpieczona
import com.example.upieczona.destination.Destination
import com.example.upieczona.dtoposts.ContentDto
import com.example.upieczona.dtoposts.GuidDto
import com.example.upieczona.dtoposts.OgImageDto
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.dtoposts.TitleDto
import com.example.upieczona.dtoposts.TwitterMiscDto
import com.example.upieczona.dtoposts.YoastHeadJsonDto
import com.example.upieczona.mainscreen.MainPageOfUpieczonaWithRecipes
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.topappbar.TopAppBarUpieczona

object MaterialsUtils {
  val colorPinkMain = Color(0xFFd6bbb4)
  val colorPink = Color(0xFFeee3df)
  val PinkUpieczona = Color(0xFFd6bbb4)
  val colorRed = Color(0xBEE02C2C)
  val colorSurface = Color.hsl(300.0F, 0.1F, 0.60F)

  val regexPatternPhotosUpieczona =
    """http://www\.upieczona\.pl/wp-content/uploads/\d{4}/\d{2}/[^"]+\.(jpg|jpeg|png)""".toRegex()

  val regexPatternTitleIngredientsUpieczona =
    """<h3 class="ingredients-title">(.*)</h3>""".toRegex()

  val regexPatternShopListUpieczona =
    """<p class="ingredient-item-name is-strikethrough-active">(.*?)</p>"""

  val regexForRecipe = """<p>(.*?)<\/p>""".toRegex()

  val ingredientsListPattern =
    Regex("<ul class=\"ingredients-list\">(.*?)</ul>", RegexOption.DOT_MATCHES_ALL)

  @Composable
  fun CreateBoxContent() {
    Box(
      modifier = Modifier
        .padding(8.dp)
        .aspectRatio(0.5f)
        .background(Color.White),
      contentAlignment = Alignment.Center
    ) {

      // Image Section
      Column(
        modifier = Modifier
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 25.dp)
            .background(Color.White), contentAlignment = Alignment.Center
        ) {
          Image(
            painter = painterResource(id = R.drawable.img8820),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }

        // favorite Section
        IconButton(
          onClick = {
            Icons.Default.Favorite
          },
          modifier = Modifier.align(Alignment.Start)
        ) {
          Icon(Icons.Default.FavoriteBorder, contentDescription = null)
        }

        // Text Section
        Box(
          modifier = Modifier
            .weight(0.5f)
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White), contentAlignment = Alignment.Center
        ) {
          Text(
            fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
            textAlign = TextAlign.Center,
            color = Color(0xFF000000),
            text = "Zielone naleśniki na słodko z twarożkiem waniliowym",
            fontSize = 14.sp
          )
        }
      }
    }
  }

}