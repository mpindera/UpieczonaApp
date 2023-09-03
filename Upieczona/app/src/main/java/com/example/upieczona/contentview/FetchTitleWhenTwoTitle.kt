package com.example.upieczona.contentview

import android.util.Log
<<<<<<< HEAD
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
=======
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.regexPatternShopListUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun FetchTitleWhenTwoTitle(
  ingredientTitleUpieczona: List<String>,
  upieczonaViewModel: UpieczonaViewModel,
  postDetails: PostsOfUpieczonaItemDto
) {


  for (i in ingredientTitleUpieczona.indices) {

    val firstIngredients =
      Regex(regexPatternShopListUpieczona)
        .findAll(upieczonaViewModel.ingredientsLists(postDetails.content.rendered)[i].groupValues[1])
        .map { it.groupValues[1] }
        .toList()

    val ingredientStates = remember {
      firstIngredients.map { title ->
        mutableStateOf(false)
      }
    }

    Text(
      modifier = Modifier.padding(5.dp),
      fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
      text = ingredientTitleUpieczona[i].uppercase(),
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    )
    if (upieczonaViewModel.ingredientsLists(postDetails.content.rendered)
        .isNotEmpty()
    ) {

      ingredientStates.forEachIndexed { index, isCheckedState ->
<<<<<<< HEAD
        Box(
          modifier = Modifier.clickable {
            isCheckedState.value = !isCheckedState.value
          }) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
              checked = isCheckedState.value,
              onCheckedChange = {
                isCheckedState.value = it
              }
            )
            Text(
              modifier = Modifier.padding(start = 8.dp, end = 8.dp),
              fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
              text = firstIngredients[index],
              fontSize = 14.sp,
              textAlign = TextAlign.Start,
              textDecoration = if (isCheckedState.value) TextDecoration.LineThrough else TextDecoration.None
            )
          }
=======
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(
            checked = isCheckedState.value,
            onCheckedChange = {
              isCheckedState.value = it
            }
          )
          Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
            text = firstIngredients[index],
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            textDecoration = if (isCheckedState.value) TextDecoration.LineThrough else TextDecoration.None
          )
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
        }
      }
    }
  }
  Divider(modifier = Modifier.padding(top = 3.dp, bottom = 3.dp))

  /* val formattedText = upieczonaViewModel.formatInstructions(
     HtmlCompat.fromHtml(
       recipeContent, HtmlCompat.FROM_HTML_MODE_LEGACY
     ).toString()
   )*/

<<<<<<< HEAD
  val recipeTitleInstruction =
    upieczonaViewModel.formatInstructionsTitle(postDetails.content.rendered)
=======
  val recipeTitleInstruction = upieczonaViewModel.formatInstructionsTitle(postDetails.content.rendered)
>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
  val up = upieczonaViewModel.fetchRecipe1(postDetails.content.rendered)

  val emTitle = upieczonaViewModel.formatInstructionsTitleEM(postDetails.content.rendered)

  var currentIndex = 0

  for ((index, recipeContent) in up.withIndex()) {
    val currentTitle = recipeTitleInstruction.getOrNull(currentIndex)

    if (emTitle.isNotEmpty() && index < emTitle.size) {
      Text(
        modifier = Modifier.padding(start = 5.dp),
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        text = emTitle[index],
        fontStyle = FontStyle.Italic
      )
    }

    if (!currentTitle.isNullOrBlank()) {
      Text(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        fontFamily = MaterialTheme.typography.headlineSmall.fontFamily,
        text = "\n$currentTitle",
        fontSize = 20.sp,
        textAlign = TextAlign.Start,
      )

      currentIndex++
    }

    Column(modifier = Modifier.padding(4.dp)) {
      Text(
        modifier = Modifier.padding(start = 5.dp),
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        text = recipeContent,
      )
    }
  }
<<<<<<< HEAD
=======


>>>>>>> 84b7352ef9f1230ad16ba355cf254e03133d2ac0
}
