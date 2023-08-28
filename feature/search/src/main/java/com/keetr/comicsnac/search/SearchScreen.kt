package com.keetr.comicsnac.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.theme.AppIcons
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onItemClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
) {
    BottomSheetScaffold(
        sheetContent = { },
//        sheetPeekHeight = 24f.dp,
        containerColor = MaterialTheme.colorScheme.tertiary,
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 64f.dp)
                    .padding(horizontal = 12f.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(AppIcons.ArrowBack, null)
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChanged,
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8f.dp)
                        .weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                )
                AnimatedVisibility(query.isNotEmpty()) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(AppIcons.Close, null)
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(16f.dp),
                verticalArrangement = Arrangement.spacedBy(16f.dp)
            ) {
                items(20) {
                    WideCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        name = "Ernesto Crawford",
                        description = "contentiones asdfsadf asdf asdfasd fasdf asdfas df asdf as df a sd fa sd fa sdg asdgsdfgdsg sdfg sdfgsdfg sdfgsdfgsd gfsdfg dsfgsdfgasdfgasdf sadfasdfasf ",
                        imageUrl = "http://www.bing.com/search?q=aenean",
                        imageDescription = "conclusionemque",
                        type = "Character",
                        background = MaterialTheme.colorScheme.secondary,
                        onClick = {},
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme {
        var query by remember {
            mutableStateOf("")
        }
        SearchScreen(query = query, onQueryChanged = { query = it }, onItemClicked = {}) {

        }
    }
}