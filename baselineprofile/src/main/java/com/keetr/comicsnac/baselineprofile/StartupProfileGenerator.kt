package com.keetr.comicsnac.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collect(
            packageName = "com.keetr.comicsnac",
            includeInStartupProfile = true
        ) {
            // This scenario just starts the activity and waits for it to draw
            // the first frame. If you have animations or async content in your
            // startup, wait for them with UiAutomator.
            startActivityAndWait()
            enterApiJourney()
            waitForHomeContent()
        }
}
