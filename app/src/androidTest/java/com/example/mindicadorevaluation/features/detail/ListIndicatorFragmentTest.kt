package com.example.mindicadorevaluation.features.detail

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindicadorevaluation.CoroutineTestRule
import com.example.mindicadorevaluation.TestActivity
import com.example.mindicadorevaluation.ViewModelFactoryTest
import com.example.mindicadorevaluation.core.models.Indicator
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ListIndicatorFragmentTest {
    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @get:Rule
    val rule = ActivityScenarioRule(TestActivity::class.java)

    private val remoteRepository: RemoteRepository = mock()
    private val netInfo: NetworkInformation = mock()
    private val authenticator: Authenticator = mock()

    private val factory =
        ViewModelFactoryTest(
            coroutineRule.testDispatcherProvider,
            remote = remoteRepository,
            netInfo = netInfo,
            auth = authenticator,
        )

    private val fragment = ListIndicatorFragment().apply {
        viewModelFactory = factory
    }

    @Before
    fun init() {
        whenever(authenticator.getUserLogged()).thenReturn("MyUser")
        whenever(netInfo.isOnline()).thenReturn(true)
    }

    @Test
    fun whenInternetIsOffMessageIsShown() {
        whenever(netInfo.isOnline()).thenReturn(false)
        launchListIndicator(rule, fragment) {

        } verify {
            internetMessageIsDisplayed()
        }
    }

    @Test
    fun whenErrorMessageIsShown() {
        runBlocking {
            whenever(remoteRepository.getIndicators()).thenReturn(Resource.Error("Error testing"))
        }

        launchListIndicator(rule, fragment) {

        } verify {
            errorMessageIsDisplayed()
        }
    }

    private val indicators = listOf(
        Indicator(
            codigo = "dollar",
            fecha = "12/03/2021",
            nombre = "Dollar",
            unidad_medida = "UM",
            valor = 1.5
        ),
        Indicator(
            codigo = "bitcoin",
            fecha = "12/03/2021",
            nombre = "Bitcoin",
            unidad_medida = "B",
            valor = 2.0
        )
    )

}
