package com.example.mindicadorevaluation.features.detail

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.TestActivity
import org.hamcrest.CoreMatchers

class ListIndicatorRobot {

    infix fun verify(block: ListIndicatorVerificationRobot.() -> Unit): ListIndicatorVerificationRobot {
        return ListIndicatorVerificationRobot().apply(block)
    }
}

class ListIndicatorVerificationRobot {
    fun internetMessageIsDisplayed() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.no_internet)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun errorMessageIsDisplayed() {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.load_error)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

fun launchListIndicator(
    rule: ActivityScenarioRule<TestActivity>,
    listIndicatorFragment: ListIndicatorFragment,
    block: ListIndicatorRobot.() -> Unit
): ListIndicatorRobot {

    rule.scenario.onActivity { activity ->
        activity.startFragment(listIndicatorFragment) {
            it.tag
        }
    }

    return ListIndicatorRobot().apply(block)
}
