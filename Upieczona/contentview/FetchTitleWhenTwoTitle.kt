package com.example.upieczona.contentview

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils.regexForRecipe
import com.example.upieczona.staticobjects.MaterialsUtils.regexPatternShopListUpieczona
import com.example.upieczona.viewmodels.UpieczonaViewModel

@Composable
fun FetchTitleWhenTwoTitle(
  ingredientTitleUpieczona: List<String>,
  upieczonaViewModel: UpieczonaViewModel,
  postDetails: PostsOfUpieczonaItemDto
) {

  val recipeContents = upieczonaViewModel.fetchRecipe(postDetails.content.rendered)

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
      }
    }
  }
  fun formatInstructions(input: String): String {
    val sentences = input.split(". ")

    val formattedSentences = mutableListOf<String>()
    for ((index, sentence) in sentences.withIndex()) {
      val formattedSentence = "${index + 1}. $sentence"
      formattedSentences.add(formattedSentence)
    }

    return formattedSentences.joinToString("\n")
  }
  for (recipeContent in recipeContents) {
    val formattedText = formatInstructions(recipeContent)
    Text(text = formattedText)
    Log.d("test1",formattedText)
  }
}