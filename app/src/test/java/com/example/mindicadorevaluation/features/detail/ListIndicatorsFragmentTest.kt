package com.example.mindicadorevaluation.features.detail

import android.os.Build
import android.os.Looper.getMainLooper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.core.models.IndicatorResponse
import com.example.mindicadorevaluation.core.utils.Resource
import com.example.mindicadorevaluation.features.detail.adapters.IndicatorAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ListIndicatorsFragmentTest{

    private var mockViewModel = mock(DetailViewModel::class.java)
    private val userName = "endherson"

    @Before
    fun setup() {
        Mockito.`when`(mockViewModel.getUserName()).thenReturn(userName)
        Mockito.`when`(mockViewModel.responseApi).thenReturn(MutableLiveData<Resource<IndicatorResponse>>())
    }

    /*
    @Test
    fun whenScreenIsVisible_showUserName() = runBlockingTest {

        FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel
                    }
                }
            })



        MatcherAssert.assertThat("a",equalTo("d"))
    }
    */

    @Test
    fun whenIsLoading_showProgressbar() = runBlockingTest {
        val response = MutableLiveData<Resource<IndicatorResponse>>()
        response.postValue(Resource.Loading())
        Mockito.`when`(mockViewModel.responseApi).thenReturn(response)
        FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel
                    }
                }
            })

        onView(withId(R.id.pbRequestApi)).check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun whenIsSuccess_hideProgressbar() = runBlockingTest {
        val response = MutableLiveData<Resource<IndicatorResponse>>()
        response.postValue(Resource.Success(ResponseUtil().getExampleIndicatorResponse()))
        Mockito.`when`(mockViewModel.responseApi).thenReturn(response)
        FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel
                    }
                }
            })

        onView(withId(R.id.pbRequestApi)).check(ViewAssertions.matches(not(isDisplayed())))

    }

    @Test
    fun whenIsSuccess_showList() = runBlockingTest {
        val response = MutableLiveData<Resource<IndicatorResponse>>()
        response.postValue(Resource.Success(ResponseUtil().getExampleIndicatorResponse()))
        Mockito.`when`(mockViewModel.responseApi).thenReturn(response)
        FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel
                    }
                }
            })

        onView(withId(R.id.rvIndicators)).check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun whenIsSuccessAndItemSelected_moveToDetail() = runBlockingTest {
        val response = MutableLiveData<Resource<IndicatorResponse>>()
        response.postValue(Resource.Success(ResponseUtil().getExampleIndicatorResponse()))
        Mockito.`when`(mockViewModel.responseApi).thenReturn(response)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_detail)


        val scenario = FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel
                    }
                }
            })

        scenario.onFragment {fragment->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.rvIndicators)).perform(actionOnItemAtPosition<IndicatorAdapter.IndicatorVH>(0, click()))

        MatcherAssert.assertThat(navController.currentDestination?.id, IsEqual.equalTo(R.id.selectedIndicatorFragment))

    }


    @Test
    fun whenIsError_showMessage() = runBlockingTest {

              val response = MutableLiveData<Resource<IndicatorResponse>>()
              response.postValue(Resource.Error("a"))
              Mockito.`when`(mockViewModel.responseApi).thenReturn(response)
              FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null, R.style.Theme_AppCompat,
                  object : FragmentFactory(){
                      override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                          return ListIndicatorsFragment().apply {
                              viewModel = mockViewModel

                          }
                      }
                  })

              shadowOf(getMainLooper()).idle()

        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.load_error)
            )
        )
            .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun whenIsNotInternet_showMessage() = runBlockingTest {

        val response = MutableLiveData<Resource<IndicatorResponse>>()
        response.postValue(Resource.NoInternet())
        Mockito.`when`(mockViewModel.responseApi).thenReturn(response)
        FragmentScenario.launchInContainer(ListIndicatorsFragment::class.java, null, R.style.Theme_AppCompat,
            object : FragmentFactory(){
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ListIndicatorsFragment().apply {
                        viewModel = mockViewModel

                    }
                }
            })

        shadowOf(getMainLooper()).idle()

        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                ViewMatchers.withText(R.string.no_internet)
            )
        )
            .check(ViewAssertions.matches(isDisplayed()))

    }

}