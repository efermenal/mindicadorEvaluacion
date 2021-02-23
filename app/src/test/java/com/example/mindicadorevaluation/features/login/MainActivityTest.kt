package com.example.mindicadorevaluation.features.login


import android.content.Intent
import android.os.Build
import android.os.Looper.getMainLooper
import android.widget.Button
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.features.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowIntent


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    lateinit var activityController: ActivityController<MainActivity>
    lateinit var activity: MainActivity

    @Before
    fun setup(){
        activityController = Robolectric.buildActivity(MainActivity::class.java)
        activityController
                        .create()
                        .start()
                        .resume()

        activity = activityController.visible().get()
    }

    @Test
    fun attemptingLogin_whenAllFieldsAreEmpty_showsMessage() = runBlockingTest {

        val btn = activity.findViewById<Button>(R.id.btn_login)
        btn.performClick()

        onView(allOf(withId(com.google.android.material.R.id.snackbar_text),
                withText(activity.getString(R.string.user_and_pass_are_empty))))
                .check(matches(isDisplayed()));

    }

    @Test
    fun attemptingLogin_whenPasswordIsEmpty_showsMessage()= runBlockingTest{

        val btn = activity.findViewById<Button>(R.id.btn_login)
        onView(withId(R.id.edt_user)).perform(typeText("endherson"))
        btn.performClick()

        onView(allOf(withId(com.google.android.material.R.id.snackbar_text),
                withText(activity.getString(R.string.user_and_pass_are_empty))))
                .check(matches(isDisplayed()));
    }

    @Test
    fun attemptingLogin_whenUserIsEmpty_showsMessage() = runBlockingTest{

        val btn = activity.findViewById<Button>(R.id.btn_login)
        onView(withId(R.id.edt_pass)).perform(typeText("endherson"))
        btn.performClick()

        onView(allOf(withId(com.google.android.material.R.id.snackbar_text),
                withText(activity.getString(R.string.user_and_pass_are_empty))))
                .check(matches(isDisplayed()));
    }

    @Test
    fun attemptingLogin_invalidUser_showsMessage() = runBlockingTest{

        val btn = activity.findViewById<Button>(R.id.btn_login)
        onView(withId(R.id.edt_user)).perform(typeText("endherson"))
        onView(withId(R.id.edt_pass)).perform(typeText("a"))
        Espresso.closeSoftKeyboard()
        btn.performClick()

        shadowOf(getMainLooper()).idle()

        onView(allOf(withId(com.google.android.material.R.id.snackbar_text),
                withText(activity.getString(R.string.user_and_pass_are_empty))))
                .check(matches(isDisplayed()));

    }

    @Test
    @LooperMode(LooperMode.Mode.PAUSED)
    fun attemptingLogin_validUser_showsDetailActivity() = runBlockingTest{

        val btn = activity.findViewById<Button>(R.id.btn_login)
        onView(withId(R.id.edt_user)).perform(typeText("endherson"))
        onView(withId(R.id.edt_pass)).perform(typeText("endherson"))


        btn.performClick()

        val expectedIntent  = Intent(activity, DetailActivity::class.java)
        val shadowActivity = shadowOf(activity)
        val actualIntent = shadowActivity.nextStartedActivity

        shadowOf(getMainLooper()).idle()

        assert(actualIntent.filterEquals(expectedIntent))
        /*
        val shadowActivity = shadowOf(activity)
        val startedIntent = shadowActivity.nextStartedActivity
        val shadowIntent: ShadowIntent = shadowOf(startedIntent)
         shadowOf(getMainLooper()).idle()

        assertThat(shadowIntent.intentClass.name, equalTo(DetailActivity::class.java!!.getName()))
        */

    }


}