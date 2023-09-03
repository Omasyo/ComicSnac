package com.keetr.comicsnac.ui.components.placeholders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.R
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.theme.ComicSnacTheme
import com.keetr.comicsnac.ui.theme.LightScheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ErrorPlaceholder(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier,
//            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ComicCard(backgroundColor = MaterialTheme.colorScheme.error) {
            Text(
                stringResource(R.string.oops),
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(20f.dp, 12f.dp)
            )
        }
        Spacer(Modifier.height(16f.dp))
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.error),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
            onRetry?.let {
                Spacer(Modifier.width(4f.dp))
                ComicCard(
                    backgroundColor = MaterialTheme.colorScheme.onError,
                    onClick = onRetry
                ) {
                    Text(
                        stringResource(R.string.retry),
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(12f.dp, 8f.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ComicSnacTheme(LightScheme) {
        ErrorPlaceholder { }
    }
}