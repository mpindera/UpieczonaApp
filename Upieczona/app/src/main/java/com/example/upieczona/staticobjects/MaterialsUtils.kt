package com.example.upieczona.staticobjects

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.example.upieczona.R

object MaterialsUtils {
  val colorPinkMain = Color(0xFFd6bbb4)
  val colorPink = Color(0xFFeee3df)
  val PinkUpieczona = Color(0xFFd6bbb4)
  val colorRed = Color(0xBEE02C2C)
  val colorCardIngredient = Color(0xFFfaf0ee)
  val colorSurface = Color.hsl(300.0F, 0.1F, 0.60F)

  val regexPatternPhotosUpieczona =
    """http://www\.upieczona\.pl/wp-content/uploads/\d{4}/\d{2}/[^"]+\.(jpg|jpeg|png)""".toRegex()

  val regexPatternTitleIngredientsUpieczona =
    """<h3 class="ingredients-title">(.*)</h3>""".toRegex()

  val regexPatternShopListUpieczona =
    """<p class="ingredient-item-name is-strikethrough-active">(.*?)</p>"""

  val ingredientsListPattern =
    Regex("<ul class=\"ingredients-list\">(.*?)</ul>", RegexOption.DOT_MATCHES_ALL)

  val recipeTitleInstruction = """<div class="entry-content"><p>(.*?)</p></div>""".toRegex()

  fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
  }

  fun Modifier.swipeToReturn(isSwipedRight: Boolean, navController: NavController): Modifier {
    return this.pointerInput(Unit) {
      detectTransformGestures { _, pan, _, zoom ->
        if (pan.x > 15f && !isSwipedRight) {
          navController.navigateUp()
        } else if (pan.x <= 100f) {

        }
      }
    }
  }


  @Composable
  fun CreateBoxContent() {
    Surface(
      shadowElevation = 10.dp
    ) {
      Box(
        modifier = Modifier
          .padding(8.dp)
          .aspectRatio(0.5f)
          .clip(RoundedCornerShape(10.dp))
          .background(Color.White),
        contentAlignment = Alignment.Center
      ) {

        // Image Section
        Column(
          modifier = Modifier
            .fillMaxSize()
            .background(colorPink),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
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
              .fillMaxWidth()
              .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
              .clip(RoundedCornerShape(5.dp))
              .background(colorPink), contentAlignment = Alignment.Center
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


}