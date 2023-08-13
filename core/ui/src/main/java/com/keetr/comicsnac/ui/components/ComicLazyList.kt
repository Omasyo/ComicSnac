package com.keetr.comicsnac.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

interface ComicLazyListScope {
    val sections: MutableList<ComicListContent>

    fun section(tag: String, content: @Composable () -> Unit)

    fun nextItem(lazyListScope: LazyListScope)
}

class ComicLazyListScopeImpl : ComicLazyListScope {
    private var currColor by mutableIntStateOf(0)
    private val nextColor get() = (currColor + 1) % 3

    private var currItem = 0
    private val nextItem get() = currItem + 1

    var addedSpacer = false
        private set


    val colors = listOf(Color(0xFF1A323E), Color(0xFF902724), Color(0xFF02537C))
    val strokeColor = Color(0xFFF7D281)

    override val sections = mutableListOf<ComicListContent>()

    val itemFinished get() = currItem >= sections.size

    override fun section(tag: String, content: @Composable () -> Unit) {
        Log.d("TAG", "section: Adding content $tag")
        sections.add(ComicListContent(tag, content))
    }

    override fun nextItem(lazyListScope: LazyListScope) {
        val tColor = colors[nextColor]
        with(lazyListScope) {
            if (addedSpacer) {
                with(sections[currItem]) {
                    item(name) {
                        Text("$currColor")
                        Box(Modifier.background(tColor)) {
                            content()
                        }
                    }
                }
                ++currItem
                addedSpacer = false
            } else {
                val flag = (currItem % 2) != 0
                item("spacer#$currItem") {
                    ComicListSeperator(
                        upperColor = colors[currColor],
                        lowerColor = colors[nextColor],
                        strokeColor = strokeColor,
                        flipped = flag
                    )
                }
                addedSpacer = true
            }
        }
    }

}

class ComicListContent(val name: String, val content: @Composable () -> Unit)

@Composable
fun ComicLazyList(
    modifier: Modifier = Modifier,
    content: ComicLazyListScope.() -> Unit
) {
    val scope = remember { ComicLazyListScopeImpl() }
    scope.content()

    LazyColumn(modifier) {
        while (!scope.itemFinished) {
            scope.nextItem(this)
        }
    }
}

@Preview
@Composable
private fun Preview() {

    ComicLazyList(Modifier.fillMaxSize()) {
        section("Test 1") {
            Box(
                Modifier
                    .padding(24f.dp)
                    .background(Color.Cyan)
                    .fillMaxWidth()
                    .height(80f.dp)
            )
        }

        section("Test 2") {
            Box(
                Modifier
                    .padding(24f.dp)
                    .background(Color.Magenta)
                    .fillMaxWidth()
                    .height(80f.dp)
            )
        }

        section("Test 3") {
            Box(
                Modifier
                    .padding(24f.dp)
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .height(80f.dp)
            )
        }

        section("Test 4") {
            Box(
                Modifier
                    .padding(24f.dp)
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .height(80f.dp)
            )
        }
    }
}