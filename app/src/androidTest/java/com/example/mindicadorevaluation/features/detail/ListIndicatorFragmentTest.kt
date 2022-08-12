package com.example.mindicadorevaluation.features.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindicadorevaluation.CoroutineTestRule
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

    private val fragmentFactory = object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return ListIndicatorFragment().apply {
                viewModelFactory = factory
            }
        }
    }

    @Before
    fun init() {
        whenever(authenticator.getUserLogged()).thenReturn("MyUser")

    }

    @Test
    fun whenInternetIsOffMessageIsShown() {
        whenever(netInfo.isOnline()).thenReturn(false)
        launchListIndicator(fragmentFactory) {

        } verify {
            internetMessageIsDisplayed()
        }
    }

    @Test
    fun whenErrorMessageIsShown() {

        whenever(netInfo.isOnline()).thenReturn(true)

        runBlocking {
            whenever(remoteRepository.getIndicators()).thenReturn(Resource.Error("Error testing"))
        }

        launchListIndicator(fragmentFactory) {

        } verify {
            errorMessageIsDisplayed()
        }
    }

    @Test
    fun whenErrorMessageIsShownI() {

        whenever(netInfo.isOnline()).thenReturn(true)

        runBlocking {
            whenever(remoteRepository.getIndicators()).thenReturn(Resource.Success(indicators))
        }

        launchListIndicator(fragmentFactory) {

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
