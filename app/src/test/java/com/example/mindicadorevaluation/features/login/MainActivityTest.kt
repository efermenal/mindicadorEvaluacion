package com.example.mindicadorevaluation.features.login

import android.widget.Button


import com.example.mindicadorevaluation.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun itShouldDisplayMessage(){
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val btn = activity.findViewById(R.id.btn_login) as Button

       btn.performClick()


    }

}