package com.keetr.comicsnac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.keetr.comicsnac.home.HomeCategoryUiState
import com.keetr.comicsnac.home.HomeRoute
import com.keetr.comicsnac.home.HomeScreen
import com.keetr.comicsnac.home.HomeUiState
import com.keetr.comicsnac.home.HomeViewModel
import com.keetr.comicsnac.home.InDevelopment
import com.keetr.comicsnac.home.Success
import com.keetr.comicsnac.model.issue.Issue
import com.keetr.comicsnac.series.SeriesDetailsScreen
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import dagger.hilt.android.AndroidEntryPoint

//import com.keetr.comicsnac.ui.theme.ComicSnacTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            ComicSnacTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeRoute(
                        onItemClicked = {},
                        onMoreCategoriesClicked = { },
                        onCharacterCategoryClicked = { },
                        onVolumeCategoryClicked = { },
                        onMovieCategoryClicked = { },
                        onSeriesCategoryClicked = { }
                    )
                }
            }
        }
    }
}

val Issues = List(30) {
    Issue(
        apiDetailUrl = "https://search.yahoo.com/search?p=qui",
        deck = "vix",
        id = 5697,
        imageUrl = "https://comicvine.gamespot.com/a/uploads/scale_small/6/67663/2710974-698.jpg",
        name = "Angelo Espinoza $it",
        siteDetailUrl = "https://search.yahoo.com/search?p=magnis"

    )
}
