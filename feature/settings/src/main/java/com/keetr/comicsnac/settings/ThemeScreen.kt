package com.keetr.comicsnac.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.keetr.comicsnac.data.settings.dataStore
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.DarkKnightScheme
import com.keetr.comicsnac.ui.theme.IronManScheme
import com.keetr.comicsnac.ui.theme.SpawnScheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeScreen(
    modifier: Modifier = Modifier,
//    selectedSchemeId: Int,
    onBackPressed: () -> Unit,
//    onClickScheme: (Int) -> Unit
) {
    val context = LocalContext.current
    suspend fun onClickScheme(id: Int) {

        val ColorSchemeKey = intPreferencesKey("color_scheme")
        context.dataStore.edit {preferences ->
            preferences[ColorSchemeKey] = id
        }
    }

    Surface(modifier) {
        Column {
            IconButton(onClick = onBackPressed, modifier = Modifier.padding(12f.dp)) {
                Icon(AppIcons.ArrowBack, null)
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    //                modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        32f.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(32f.dp),
//                    maxItemsInEachRow = 3
                ) {
                    ColorSchemes.forEach { (id, scheme) ->
                        Column(
                            Modifier
                                .fillMaxWidth(0.35f)
                        ) {
                            val scope = rememberCoroutineScope()
                            ThemeBox(
                                scheme,
                                if (false) Modifier.border(
                                    4f.dp,
                                    MaterialTheme.colorScheme.onSurface
                                ) else Modifier.clickable {
                                    scope.launch {
                                        onClickScheme(id)
                                    }
                                }
                            )
//                            Text(stringResource(id))
                        }
                    }
                }
                Spacer(Modifier.height(48f.dp))
                Text("Select A Color Scheme", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun ThemeBox(
    colorScheme: ColorScheme,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier.aspectRatio(10f / 6f)) {
        drawRect(colorScheme.primary)

        val w = size.width
        val h = size.height
        val mLargeGap = 0.25f
        val mSmallGap = 0.15f
        val tLargeGap = 0.45f
        val tSmallGap = 0.35f

        val secondary = Path().apply {
            moveTo(w * mLargeGap, 0f)
            lineTo(w * (tLargeGap), 0f)
            lineTo(w * (tSmallGap), h)
            lineTo(w * mSmallGap, h)
            close()
        }
        drawPath(secondary, colorScheme.secondary)

        val tertiary = Path().apply {
            moveTo(w * (1 - tSmallGap), 0f)
            lineTo(w * (1 - mSmallGap), 0f)
            lineTo(w * (1 - mLargeGap), h)
            lineTo(w * (1 - tLargeGap), h)
            close()
        }
        drawPath(tertiary, colorScheme.tertiary)

        val onSurface = Path().apply {
            moveTo(w * tLargeGap, 0f)
            lineTo(w * (1 - tSmallGap), 0f)
            lineTo(w * (1 - tLargeGap), h)
            lineTo(w * tSmallGap, h)
            close()
        }
        drawPath(onSurface, colorScheme.onSurface)
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        ThemeScreen( onBackPressed = {})
    }
}