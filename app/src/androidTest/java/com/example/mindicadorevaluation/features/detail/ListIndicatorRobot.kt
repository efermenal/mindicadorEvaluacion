package com.example.mindicadorevaluation.features.detail

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.mindicadorevaluation.R
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
    fragmentFactory: FragmentFactory,
    block: ListIndicatorRobot.() -> Unit
): ListIndicatorRobot {
    launchFragmentInContainer<ListIndicatorFragment>(
        fragmentArgs = null,
        R.style.Theme_MindicadorEvaluation,
        factory = fragmentFactory
    )

    return ListIndicatorRobot().apply(block)
}
