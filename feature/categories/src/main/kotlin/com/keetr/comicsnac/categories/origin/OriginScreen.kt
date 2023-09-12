package com.keetr.comicsnac.categories.origin

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.categories.R
import com.keetr.comicsnac.model.LayoutType
import com.keetr.comicsnac.model.origin.OriginBasic
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun OriginScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit,
    onItemClicked: (String) -> Unit,
    origins: LazyPagingItems<OriginBasic>
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.tertiary,
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            AppIcons.ArrowBack,
                            stringResource(com.keetr.comicsnac.ui.R.string.back_button_desc)
                        )
                    }
                }
            )
        }
    ) { padding ->
        AnimatedContent(
            targetState = origins.loadState.refresh,
            label = "CategoryContent",
            modifier = Modifier.padding(padding)
        ) { refreshState ->
            when (refreshState) {
                is LoadState.Error -> ErrorPlaceholder(Modifier.fillMaxSize()) { origins.retry() }
                LoadState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is LoadState.NotLoading -> LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16f.dp),
                    columns = GridCells.Adaptive(144f.dp),
                    horizontalArrangement = Arrangement.spacedBy(16f.dp),
                    verticalArrangement = Arrangement.spacedBy(32f.dp),
                    state = rememberLazyGridState()
                ) {
                    items(origins.itemCount) {
                        with(origins[it]!!) {
                            Text(
                                name,
                                Modifier
                                    .clickable { onItemClicked(apiDetailUrl) }
                                    .padding(horizontal = 16f.dp),
                                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                            )
                        }
                    }
                    if (origins.loadState.append == LoadState.Loading) {
                        item {
                            LoadingPlaceholder()
                        }
                    }
                }
            }
        }
    }
}