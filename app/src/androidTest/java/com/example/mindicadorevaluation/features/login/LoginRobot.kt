package com.example.mindicadorevaluation.features.login

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.mindicadorevaluation.R
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertEquals

class LoginRobot(
    private val navController: NavController?,
) {

    fun typeUser(username: String) {
        onView(withId(R.id.edt_user)).perform(typeText(username))
    }

    fun typePassword(password: String) {
        onView(withId(R.id.edt_pass)).perform(typeText(password))
    }

    fun tapOnLogin() {
        onView(withId(R.id.btn_login)).perform(ViewActions.click())
    }

    infix fun verify(
        block: LoginVerificationRobot.() -> Unit
    ): LoginVerificationRobot {
        return LoginVerificationRobot(navController).apply(block)
    }
}

class LoginVerificationRobot(
    private val navController: NavController?
) {

    fun emptyCredentialMessageIsShown() {
        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.user_and_pass_are_empty)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun invalidCredentialsMessageIsShown() {
        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.login_invalid)
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun navigateToMainScreen() {
        assertEquals(navController?.currentDestination?.id, R.id.listIndicatorsFragment)
    }

}

fun launchLogin(
    navController: NavController? = null,
    block: LoginRobot.() -> Unit
): LoginRobot {
    return LoginRobot(navController).apply(block)
}
