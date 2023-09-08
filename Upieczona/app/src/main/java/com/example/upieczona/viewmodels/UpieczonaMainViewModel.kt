package com.example.upieczona.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.mainscreen.MainPageState
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.topappbar.WidgetState
import org.jsoup.Jsoup

class UpieczonaMainViewModel : ViewModel() {
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

  fun searchItemsFromTags(
    listPost: List<PostsOfUpieczonaItemDto>, selectedCategory: List<Int?>
  ): List<PostsOfUpieczonaItemDto> {
    return if (selectedCategory.isEmpty()) {
      emptyList()
    } else {
      listPost.filter { post ->
        selectedCategory.all {
          it in post.tags
        }.and(selectedCategory.containsAll(post.tags))
      }.toMutableList()
    }
  }

  fun formatInstructionsTitleEM(input: String): List<String> {
    val document = Jsoup.parse(input)
    return document.select("p:has(em)").map {
      it.toString().replace("<p>", "").replace("<em>", "").replace("</p>", "")
        .replace("</em>", "\n")
    }.toList()
  }
  private val ingredientCache = mutableMapOf<String, List<String>>()
  fun formatInstructionsTitle(input: String): List<String> {
    val regex = """<strong>(.*)<\/strong>""".toRegex()
    return regex.findAll(input).map { it.groupValues[1] }.toList()
  }

  fun getCachedIngredients(content: String): List<String> {
    if (ingredientCache.containsKey(content)) {
      return ingredientCache[content]!!
    }

    val ingredients = extractIngredients(content)
    ingredientCache[content] = ingredients
    return ingredients
  }

  fun extractIngredients(content: String): List<String> {
    val matches = MaterialsUtils.regexPatternTitleIngredientsUpieczona.findAll(content)
    return matches.map { it.groupValues[1] }.toList()
  }

  fun ingredientsLists(content: String): List<MatchResult> {
    return MaterialsUtils.ingredientsListPattern.findAll(content).toList()
  }

  fun extractPhotosUrls(content: String): List<String> {
    return MaterialsUtils.regexPatternPhotosUpieczona.findAll(content).map { it.value }.toList()
      .distinct()
  }

  fun fetchRecipe1(input: String): List<String> {
    val pattern = "<p>.*?</p>".toRegex()
    val matches = pattern.findAll(input).map { it.value }.toList()

    val document = Jsoup.parse(matches.toString())

    val paragraphs = document.select("p:not(:has(em))").map { paragraph ->
      paragraph.html()  // Pobierz treść HTML akapitu
        .replace("<br>", "\n   ").replace(Regex("<strong>.*?</strong>"), "").replace("<p>", "\n")
        .replace("</p>", "\n")
    }

    return paragraphs
  }
  fun navigateToTagsPage(selectedItemsMap: MutableMap<Int, Int>, navController: NavController) {
    val listOfFilters = selectedItemsMap.values
    val postIndexString = listOfFilters.joinToString(", ")
    val routeWithArgument = "FilterPage/$postIndexString"
    navController.navigate(routeWithArgument)
  }

}