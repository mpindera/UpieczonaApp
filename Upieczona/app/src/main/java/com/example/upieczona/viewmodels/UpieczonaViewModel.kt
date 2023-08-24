package com.example.upieczona.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upieczona.api.UpieczonaApi
import com.example.upieczona.dtocategories.CategoriesOfUpieczonaItemDto
import com.example.upieczona.dtoposts.PostsOfUpieczonaItemDto
import com.example.upieczona.staticobjects.MaterialsUtils
import com.example.upieczona.staticobjects.MaterialsUtils.ingredientsListPattern
import com.example.upieczona.staticobjects.MaterialsUtils.recipeTitleInstruction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class UpieczonaViewModel(
  private val api: UpieczonaApi,
) : ViewModel() {


  var select = MutableStateFlow(0)

  val categoriesState = MutableStateFlow(emptyList<CategoriesOfUpieczonaItemDto>())

  val stateNames = MutableStateFlow(emptyList<PostsOfUpieczonaItemDto>())

  private val _allPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allPosts: State<List<PostsOfUpieczonaItemDto>> = _allPosts

  private val _allCategoryPosts = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val allCategoryPosts: State<List<PostsOfUpieczonaItemDto>> = _allCategoryPosts

  private val _error = MutableStateFlow<String?>(null)
  val error: StateFlow<String?> = _error

  private val _postDetails = mutableStateOf<List<PostsOfUpieczonaItemDto>>(emptyList())
  val postDetails: State<List<PostsOfUpieczonaItemDto>> = _postDetails


  private val _isLoading = MutableStateFlow(false)
  val isLoading: StateFlow<Boolean> = _isLoading

  init {
    fetchData()
  }

  fun fetchData() {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        _isLoading.value = true

        val response = api.fetchAllCategories()
        val responseNames = api.fetchAllPostsFromFirstPage()

        stateNames.value = responseNames
        categoriesState.value = response

      } catch (e: Exception) {
        Log.e("UpieczonaViewModel", "Error fetching data: ${e.message}")
      } finally {
        _isLoading.value = false
      }
    }
  }

  suspend fun fetchPostById(postId: Int) {
    try {
      val response = api.fetchPostsDetails(postId)

      _postDetails.value = listOf(response.first())
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

  suspend fun fetchAllPosts() {
    try {
      _allPosts.value = emptyList()
      coroutineScope {
        var page = 1
        while (true) {
          val posts = api.fetchPostsFromFirstPage(page)

          if (posts.isNotEmpty()) {
            _allPosts.value = _allPosts.value + posts
            page++
          } else {
            break
          }
        }
      }
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

  suspend fun fetchAllPostsByCategory(categoryId: Int) {
    try {
      _allCategoryPosts.value = emptyList()
      var page = 1
      var posts: List<PostsOfUpieczonaItemDto>
      do {
        posts = api.fetchAllCategoryPosts(categoryId, page)
        _allCategoryPosts.value = _allCategoryPosts.value + posts
        page++
      } while (posts.isNotEmpty())
    } catch (e: Exception) {
      _error.value = "Error fetching post details"
      Log.e("UpieczonaViewModel", error.value.toString())
    }
  }

  private val ingredientCache = mutableMapOf<String, List<String>>()

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
    return ingredientsListPattern.findAll(content).toList()
  }

  fun extractPhotosUrls(content: String): List<String> {
    return MaterialsUtils.regexPatternPhotosUpieczona.findAll(content)
      .map { it.value }
      .toList()
      .distinct()
  }

  fun ingredientsListsTEST(content: String): List<MatchResult> {
    return recipeTitleInstruction.findAll(content).toList()
  }

  fun fetchRecipe1(input: String): List<String> {
    val pattern = "<p>.*?</p>".toRegex()
    val matches = pattern.findAll(input).map { it.value }.toList()

    val document = Jsoup.parse(matches.toString())

    val paragraphs = document.select("p:not(:has(em))").map { paragraph ->
      paragraph.html()  // Pobierz treść HTML akapitu
        .replace("<br>", "\n   ")
        .replace(Regex("<strong>.*?</strong>"), "")
        .replace("<p>", "\n")
        .replace("</p>", "\n")
    }

    return paragraphs
  }


  fun formatInstructionsTitle(input: String): List<String> {
    val regex = """<strong>(.*)<\/strong>""".toRegex()
    return regex.findAll(input).map { it.groupValues[1] }.toList()
  }

  fun formatInstructionsTitleEM(input: String): List<String> {
    val document = Jsoup.parse(input)
    return document.select("p:has(em)").map {
      it.toString()
        .replace("<p>", "")
        .replace("<em>", "")
        .replace("</p>", "")
        .replace("</em>", "\n")
    }.toList()
  }

}
