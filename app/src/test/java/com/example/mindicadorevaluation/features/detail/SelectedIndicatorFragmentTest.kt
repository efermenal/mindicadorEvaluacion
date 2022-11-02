package com.example.mindicadorevaluation.features.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.core.models.Indicator
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SelectedIndicatorFragmentTest {

    @Test
    fun wheScreenIsVisible_showItem(){

        val argumentIndicator =  Indicator( "dolar","01/01/2021","name","",0.0)
        val bundle = Bundle()
        bundle.putSerializable("indicator", argumentIndicator)

        FragmentScenario.launchInContainer(SelectedIndicatorFragment::class.java, bundle, R.style.Theme_AppCompat,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return SelectedIndicatorFragment()
                }
            })

        onView(ViewMatchers.withId(R.id.nameIndicator)).check(ViewAssertions.matches(ViewMatchers.withText(argumentIndicator.name)))

    }

}
