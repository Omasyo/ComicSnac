package com.keetr.comicsnac.ui.placeholders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keetr.comicsnac.ui.components.cards.ComicCard
import com.keetr.comicsnac.ui.theme.ComicSnacTheme

@Composable
fun ErrorPlaceholder(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ComicCard(backgroundColor = MaterialTheme.colorScheme.error) {
            Text(
                "Oops",
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(20f.dp, 12f.dp)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "An Error Occured",
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
                        "Retry",
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
    ComicSnacTheme {
        ErrorPlaceholder() { }
    }
}