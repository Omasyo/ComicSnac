package com.keetr.comicsnac.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the Generate Baseline Profile run configuration,
 * or directly with `generateBaselineProfile` Gradle task:
 * ```
 * ./gradlew :app:generateReleaseBaselineProfile -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=BaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collect("com.keetr.comicsnac") {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.

            // Start default activity for your app
            pressHome()
            startActivityAndWait()

            enterApiJourney()
            waitForHomeContent()
            scrollHomeScreenCarousels()
            searchJourney()
            waitForHomeContent()
            navigatePublisherJourney()
        }
    }
}

fun MacrobenchmarkScope.waitForHomeContent() {
    device.wait(Until.hasObject(By.res("home_list")), 20_000)
}

//fun MacrobenchmarkScope.scrollHomeScreen() {
//    val homeList = device.findObject(By.res("home_list"))
//    // Set gesture margin to avoid triggering gesture navigation
//    homeList.setGestureMargin(device.displayWidth / 5)
////    sectionList.scrollUntil(Direction.DOWN, Until.scrollFinished(Direction.DOWN))
//    homeList.fling(Direction.DOWN)
//
////    homeList.setGestureMargin(device.displayWidth / 5)
//////    sectionList.scrollUntil(Direction.UP, Until.scrollFinished(Direction.UP))
////    homeList.fling(Direction.UP)
//
//    device.waitForIdle()
//}
