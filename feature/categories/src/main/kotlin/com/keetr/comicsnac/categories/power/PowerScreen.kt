package com.keetr.comicsnac.categories.power

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.keetr.comicsnac.model.power.PowerBasic
import com.keetr.comicsnac.ui.components.placeholders.ErrorPlaceholder
import com.keetr.comicsnac.ui.components.placeholders.LoadingPlaceholder
import com.keetr.comicsnac.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PowerScreen(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit,
    onItemClicked: (String) -> Unit,
    powers: LazyPagingItems<PowerBasic>
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
            targetState = powers.loadState.refresh,
            label = "CategoryContent",
            modifier = Modifier.padding(padding)
        ) { refreshState ->
            when (refreshState) {
                is LoadState.Error -> ErrorPlaceholder(Modifier.fillMaxSize()) { powers.retry() }
                LoadState.Loading -> LoadingPlaceholder(Modifier.fillMaxSize())
                is LoadState.NotLoading -> LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16f.dp),
                    columns = GridCells.Adaptive(144f.dp),
                    horizontalArrangement = Arrangement.spacedBy(16f.dp),
                    verticalArrangement = Arrangement.spacedBy(32f.dp),
                    state = rememberLazyGridState()
                ) {
                    items(powers.itemCount) {
                        with(powers[it]!!) {
                            Text(
                                name,
                                Modifier
                                    .clickable { onItemClicked(apiDetailUrl) }
                                    .padding(horizontal = 16f.dp),
                                style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                            )
                        }
                    }
                    if (powers.loadState.append == LoadState.Loading) {
                        item {
                            LoadingPlaceholder()
                        }
                    }
                }
            }
        }
    }
}