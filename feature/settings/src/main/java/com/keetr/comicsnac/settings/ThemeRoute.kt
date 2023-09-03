package com.keetr.comicsnac.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ThemeRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val key = intPreferencesKey("color_scheme")
//    fun onClickScheme(id: Int) {
//        scope.launch {
//            context.dataStore.edit { preferences ->
//                preferences[key] = id
//            }
//        }
//    }
//
//    val selectedSchemeId = remember {
//        context.dataStore.data.map { preferences ->
//            preferences[key] ?: 0
//        }
//    }.collectAsState(0).value

    ThemeScreen(
        modifier = modifier,
        selectedSchemeId = viewModel.selectedSchemeId.collectAsState().value,
        onBackPressed = onBackPressed,
        onClickScheme = viewModel::onClickScheme
    )
}