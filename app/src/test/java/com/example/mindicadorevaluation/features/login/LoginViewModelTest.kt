package com.example.mindicadorevaluation.features.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mindicadorevaluation.CoroutineTestRule
import com.example.mindicadorevaluation.core.crypto.Base64Cipher
import com.example.mindicadorevaluation.core.crypto.Base64CipherTesting
import com.example.mindicadorevaluation.core.crypto.Encryption
import com.example.mindicadorevaluation.core.crypto.KeysRepository
import com.example.mindicadorevaluation.core.models.User
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.db.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.times

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val encryption = mock(Encryption::class.java)

    private lateinit var base64Test: Base64Cipher

    private val keys = mock(KeysRepository::class.java)

    private val userDao = mock(UserDao::class.java)

    private val auth = mock(Authenticator::class.java)

    private val commandCaptor: ArgumentCaptor<LoginViewModel.Command> =
        ArgumentCaptor.forClass(LoginViewModel.Command::class.java)

    private val viewStateCaptor: ArgumentCaptor<LoginViewModel.ViewState> =
        ArgumentCaptor.forClass(LoginViewModel.ViewState::class.java)

    private val viewStateObserver: Observer<LoginViewModel.ViewState> = mock()

    private val commandObserver: Observer<LoginViewModel.Command> = mock()

    private val viewModel: LoginViewModel by lazy {
        val vm = LoginViewModel(encryption, userDao, coroutineRule.testDispatcherProvider, auth)
        vm.viewState.observeForever(viewStateObserver)
        vm.command.observeForever(commandObserver)
        vm
    }

    private val user = "user"
    private val pass = "pass"

    @Before
    fun init() {
        base64Test = Base64CipherTesting()
        Mockito.`when`(keys.getSecretKey()).thenReturn("estoesunasuperasswordparaprobarl")
    }

    @After
    fun tearDown() {
        viewModel.viewState.removeObserver(viewStateObserver)
        viewModel.command.removeObserver(commandObserver)
    }


    @Test
    fun attemptingLogin_credentialInvalids_returnsInvalid() = runTest {
        givenInvalidCredentials()
        viewModel.attemptingLogin(user, pass)

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertTrue(commandCaptor.value is LoginViewModel.Command.InvalidUser)
    }

    @Test
    fun attemptingLogin_validCredentials_returnsValid() = runTest {
        giveRightCredentials()
        viewModel.attemptingLogin(user, pass)

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertTrue(commandCaptor.value is LoginViewModel.Command.NavigateToMainPage)
    }

    @Test
    fun attemptingLogin_emptyCredentials_returnsEmptyCredentials() = runTest {
        val emptyUser = ""
        val emptyPass = ""
        viewModel.attemptingLogin(emptyUser, emptyPass)

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertTrue(commandCaptor.value is LoginViewModel.Command.EmptyCredentials)
    }

    @Test
    fun showAndHideLoadingWhenLoginIsValid() {
        giveRightCredentials()
        viewModel.attemptingLogin(user, pass)

        verify(viewStateObserver, times(3)).onChanged(viewStateCaptor.capture())
        val loadingStates = viewStateCaptor.allValues.map { it.isLoading }

        assertEquals(listOf(false, true, false), loadingStates)
    }

    @Test
    fun showAndHideLoadingWhenLoginIsInvalid() {
        givenInvalidCredentials()
        viewModel.attemptingLogin(user, pass)

        verify(viewStateObserver, times(3)).onChanged(viewStateCaptor.capture())
        val loadingStates = viewStateCaptor.allValues.map { it.isLoading }

        assertEquals(listOf(false, true, false), loadingStates)
    }

    private fun givenInvalidCredentials() {
        val list = flowOf(listOf(User(user, pass)))
        Mockito.`when`(userDao.getUserById(user)).thenReturn(list)
        Mockito.`when`(encryption.encode(pass)).thenReturn("another_pass")
    }

    private fun giveRightCredentials() {
        val list = flowOf(listOf(User(user, pass)))
        Mockito.`when`(userDao.getUserById(user)).thenReturn(list)
        Mockito.`when`(encryption.encode(pass)).thenReturn(pass)
    }
}
