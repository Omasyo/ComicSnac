package com.keetr.comicsnac.baselineprofile

import android.util.Log
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
//class ApiKeyJourneyBenchmarks {
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
//            iterations = 5,
//            setupBlock = {
//                pressHome()
//                startActivityAndWait()
//            },
//            measureBlock = {
//                enterApiJourney()
//            }
//        )
//    }
//}

fun MacrobenchmarkScope.enterApiJourney() {
    device.wait(Until.hasObject(By.res("auth_screen")), 5_000)
    val screen = device.findObject(By.res("auth_screen")) ?: return
//    homeList.setGestureMargin(device.displayWidth / 5)

    val textField = screen.findObject(By.res("text_field"))
    val button = screen.findObject(By.res("verify_button"))

//    textField.click()

//    ApiKey.forEach {
//        device.pressKeyCode(KeyEvent.keyCodeFromString("KEYCODE_${it.uppercaseChar()}"))
//    }

    textField.text = ApiKey
    button.click()

    Log.i("TAG", "enterApiJourney: Tapped apikey verify")
    var state = device.wait(Until.gone(By.res("auth_screen")), 10_000)
    Log.i("TAG", "enterApiJourney: Tapped apikey verify result $state")

    while(!state) {
        screen.findObject(By.res("verify_button")).click()
        state = device.wait(Until.gone(By.res("auth_screen")), 10_000)
        Log.i("TAG", "enterApiJourney: Tapped apikey verify result $state")
    }
}
