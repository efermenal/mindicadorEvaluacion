package com.example.mindicadorevaluation.features.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindicadorevaluation.CoroutineTestRule
import com.example.mindicadorevaluation.R
import com.example.mindicadorevaluation.ViewModelFactoryTest
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.models.User
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.db.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val auth: Authenticator = mock()
    private val encryption: Encryption = mock()
    private val userDao: UserDao = mock()
    private val loginViewModelViewModelFactoryTest =
        ViewModelFactoryTest(encryption, userDao, coroutineRule.testDispatcherProvider, auth)

    private val username = "user"
    private val password = "password"
    private val anotherPassword = "anotherPassword"

    private val testNavController =
        TestNavHostController(ApplicationProvider.getApplicationContext())

    private val fragmentFactory = object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return LoginFragment().apply {
                viewModelFactory = loginViewModelViewModelFactoryTest
            }
        }
    }

    @Before
    fun init() {
        val loginScenario = launchFragmentInContainer<LoginFragment>(
            fragmentArgs = null,
            R.style.Theme_MindicadorEvaluation,
            factory = fragmentFactory,
        )
        loginScenario.onFragment {
            testNavController.setGraph(R.navigation.nav_detail)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(it.requireView(), testNavController)
        }
    }

    @Test
    fun showMessageWhenFieldsAreEmpty() {
        launchLogin {
            tapOnLogin()
        } verify {
            emptyCredentialMessageIsShown()
        }
    }

    @Test
    fun showMessageWhenCredentialAreInvalid() {
        givenUser()
        givenWrongPassword()

        launchLogin {
            typeUser(password)
            typePassword(password)
            tapOnLogin()
        } verify {
            invalidCredentialsMessageIsShown()
        }

    }

    @Test
    fun navigateToMainScreenWhenCredentialAreValid() {
        givenUser()
        givenCorrectPassword()

        launchLogin(testNavController) {
            typeUser(password)
            typePassword(password)
            tapOnLogin()
        } verify {
            navigateToMainScreen()
        }

    }

    private fun givenUser() {
        whenever(userDao.getUserById(any())).thenAnswer {
            flow {
                emit(listOf(User(username, password)))
            }
        }
    }

    private fun givenWrongPassword() {
        whenever(encryption.encode(any())).thenReturn(anotherPassword)
    }

    private fun givenCorrectPassword() {
        whenever(encryption.encode(any())).thenReturn(password)
    }

}
