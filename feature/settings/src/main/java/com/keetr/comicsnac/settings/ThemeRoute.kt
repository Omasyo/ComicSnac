package com.keetr.comicsnac.settings

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.keetr.comicsnac.data.settings.dataStore
import com.keetr.comicsnac.ui.theme.ColorSchemes
import com.keetr.comicsnac.ui.theme.DefaultScheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun ThemeRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val key = intPreferencesKey("color_scheme")
    fun onClickScheme(id: Int) {
        scope.launch {
            context.dataStore.edit { preferences ->
                preferences[key] = id
            }
        }
    }

    val selectedSchemeId = remember {
        context.dataStore.data.map { preferences ->
            preferences[key] ?: 0
        }
    }.collectAsState(0).value

    ThemeScreen(
        modifier = modifier,
        selectedSchemeId = selectedSchemeId,
        onBackPressed = onBackPressed,
        onClickScheme = ::onClickScheme
    )
}