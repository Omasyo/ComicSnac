package com.keetr.comicsnac.baselineprofile

import android.view.KeyEvent
import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//@LargeTest
//class PublisherJourneyBenchmarks {
//
//    @get:Rule
//    val rule = MacrobenchmarkRule()
//
//    @Test
//    fun startupCompilationNone() =
//        benchmark(CompilationMode.None())
//
//    @Test
//    fun startupCompilationBaselineProfiles() =
//        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))
//
//    private fun benchmark(compilationMode: CompilationMode) {
//        rule.measureRepeated(
//            packageName = "com.keetr.comicsnac",
//            metrics = listOf(FrameTimingMetric()),
//            compilationMode = compilationMode,
//            startupMode = StartupMode.WARM,
//            iterations = 1,
//            setupBlock = {
//                pressHome()
//                startActivityAndWait()
//            },
//            measureBlock = {
//                enterApiJourney()
//                waitForHomeContent()
//                scrollHomeScreenCarousels()
//                searchJourney()
//                navigatePublisherJourney()
//            }
//        )
//    }
//}

fun MacrobenchmarkScope.scrollHomeScreenCarousels() {
    val homeList = device.findObject(By.res("home_list"))
    device.wait(Until.hasObject(By.res("issues_carousel")), 20_000)
    device.wait(Until.hasObject(By.res("category_row")), 20_000)

    device.findObject(By.res("issues_carousel"))?.fling(Direction.RIGHT)
    device.waitForIdle()
    device.findObject(By.res("category_row"))?.fling(Direction.RIGHT)
    device.waitForIdle()
}

fun MacrobenchmarkScope.searchJourney() {
    device.wait(Until.hasObject(By.res("search_button")), 10_000)
    val searchButton = device.findObject(By.res("search_button")) ?: return
    searchButton.click()
    device.waitForIdle()
    device.wait(Until.hasObject(By.res("search_field")), 10_000)

    val searchField = device.findObject(By.res("search_field"))

    searchField.click()
    device.waitForIdle()
    searchField.text = "spiderman"
    device.pressKeyCode(KeyEvent.KEYCODE_ENTER)
    device.pressBack()
//    device.pressSearch()

    device.wait(Until.hasObject(By.res("search_results")), 20_000)
    device.findObject(By.res("search_results"))?.fling(Direction.DOWN)
    device.waitForIdle()
    device.findObject(By.res("search_results"))?.fling(Direction.UP)
    device.waitForIdle()
    device.pressBack()
    device.waitForIdle()
}

fun MacrobenchmarkScope.navigatePublisherJourney() {
    val homeList = device.findObject(By.res("home_list")) ?: return
    homeList.setGestureMargin(device.displayWidth / 5)

    homeList.fling(Direction.DOWN)

    homeList.wait(Until.hasObject(By.res("publisher_carousel")), 20_000)
    val publisherCarousel = homeList.findObject(By.res("publisher_carousel")) ?: return
    val publisher = publisherCarousel.findObject(By.res("centered"))
    publisher.click()
    device.wait(Until.gone(By.res("home_list")), 20_000)

    device.wait(Until.hasObject(By.res("publisher_screen")), 20_000)
    val publisherScreen = device.findObject(By.res("publisher_screen")) ?: return
    // Set gesture margin to avoid triggering gesture navigation
    publisherScreen.setGestureMargin(device.displayWidth / 5)
    publisherScreen.fling(Direction.DOWN)

    device.waitForIdle()
    device.wait(Until.hasObject(By.res("volumes_panel")), 20_000)
    device.wait(Until.hasObject(By.res("characters_panel")), 20_000)

    val webView = device.findObject(By.res("web_view"))
    val webViewExpandButton = webView.findObject(By.res("expand_button"))
    webViewExpandButton.click()

    device.waitForIdle()
    webView.fling(Direction.DOWN)
    device.waitForIdle()

    webViewExpandButton.click()
    device.waitForIdle()


//    val characters = device.findObject(By.res("characters_panel")) ?: return
//    characters.wait(Until.hasObject(By.res("lazy_grid")), 20_000)
//
//    characters.findObject(By.res("lazy_grid")).fling(Direction.RIGHT)
//    device.waitForIdle()
//
//    characters.findObject(By.res("expand_button")).click()
//    device.waitForIdle()
//    characters.findObject(By.res("lazy_grid")).fling(Direction.RIGHT)
//    device.waitForIdle()
//    device.pressBack()
//    device.waitForIdle()
    device.pressBack()
}
