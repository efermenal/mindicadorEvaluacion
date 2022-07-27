package com.example.mindicadorevaluation.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mindicadorevaluation.CoroutineTestRule
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.internal.verification.Times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class ListIndicatorViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    private val auth = Mockito.mock(Authenticator::class.java)

    private val remote = Mockito.mock(RemoteRepository::class.java)

    private val netInfo = Mockito.mock(NetworkInformation::class.java)

    private val viewStateObserver: Observer<ListIndicatorViewModel.ViewState> = mock()
    private val commandObserver: Observer<ListIndicatorViewModel.Command> = mock()
    private val viewStateCaptor: ArgumentCaptor<ListIndicatorViewModel.ViewState> =
        ArgumentCaptor.forClass(ListIndicatorViewModel.ViewState::class.java)
    private val commandCaptor: ArgumentCaptor<ListIndicatorViewModel.Command> =
        ArgumentCaptor.forClass(ListIndicatorViewModel.Command::class.java)

    private val viewModel: ListIndicatorViewModel by lazy {
        val vm = ListIndicatorViewModel(remote, netInfo, auth)
        vm.viewState.observeForever(viewStateObserver)
        vm.command.observeForever(commandObserver)
        vm
    }

    private val userName = "user"

    @Before
    fun init() {
        Mockito.`when`(auth.getUserLogged()).thenReturn(userName)
    }

    @After
    fun tearDown() {
        viewModel.viewState.removeObserver(viewStateObserver)
        viewModel.command.removeObserver(commandObserver)
    }

    @Test
    fun getIndicators_whenIsOffline_return_NoInternet() = runTest {
        givenOfflineConnection()
        viewModel.getIndicators()

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertTrue(commandCaptor.value is ListIndicatorViewModel.Command.NoInternet)
    }

    @Test
    fun getIndicators_whenIsOnLine_return_Success() = runTest {
        val response = ResponseUtil.indicatorList
        givenSuccess()
        viewModel.getIndicators()

        verify(viewStateObserver, Times(3)).onChanged(viewStateCaptor.capture())

        assertEquals(response, viewStateCaptor.value.indicators)
    }

    @Test
    fun getIndicators_whenExceptionShowError() = runTest {
        givenException()
        viewModel.getIndicators()

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertEquals(ListIndicatorViewModel.Command.Error, commandCaptor.value)
    }

    @Test
    fun getIndicators_showLoadSequenceWhenExceptionPath() = runTest {
        givenException()
        viewModel.getIndicators()

        verify(viewStateObserver, Times(3)).onChanged(viewStateCaptor.capture())

        val states = viewStateCaptor.allValues.map { it.isLoading }
        assertEquals(listOf(false, true, false), states)
    }

    @Test
    fun getIndicators_showLoadSequenceWhenIsOffline() = runTest {
        givenOfflineConnection()
        viewModel.getIndicators()

        verify(viewStateObserver, Times(3)).onChanged(viewStateCaptor.capture())

        val states = viewStateCaptor.allValues.map { it.isLoading }
        assertEquals(listOf(false, true, false), states)
    }

    @Test
    fun getIndicators_showLoadSequenceWhenSuccess() = runTest {
        givenSuccess()
        viewModel.getIndicators()

        verify(viewStateObserver, Times(3)).onChanged(viewStateCaptor.capture())

        val states = viewStateCaptor.allValues.map { it.isLoading }
        assertEquals(listOf(false, true, false), states)
    }

    @Test
    fun logout_sendCommandLogout() = runTest {
        viewModel.logout()

        verify(commandObserver).onChanged(commandCaptor.capture())

        assertEquals(ListIndicatorViewModel.Command.LogOut, commandCaptor.value)
    }

    @Test
    fun logout_call_setIsLogged_false() = runTest {
        viewModel.logout()

        verify(auth).setIsLogged(false)
    }

    @Test
    fun getUserName_checkUserFromRepository() {
        assertEquals(userName, viewModel.viewState.value?.userName)
    }

    @Test
    fun setInputSearch_listFiltered() = runTest {
        givenSuccess()
        val inputSearch = "u"
        viewModel.getIndicators()

        viewModel.setInputSearch(inputSearch)

        assertEquals(2, viewModel.viewState.value?.selectedIndicators?.count())
        assertEquals(inputSearch, viewModel.viewState.value?.indicatorInput)
    }

    @Test
    fun setInputSearch_listFilteredNonExistent() = runTest {
        givenSuccess()
        val inputSearch = "aaaa"
        viewModel.getIndicators()

        viewModel.setInputSearch(inputSearch)

        assertEquals(0, viewModel.viewState.value?.selectedIndicators?.count())
        assertEquals(inputSearch, viewModel.viewState.value?.indicatorInput)
    }

    private suspend fun givenException() {
        val exception = RuntimeException("testing")
        Mockito.`when`(netInfo.isOnline()).thenReturn(true)
        Mockito.`when`(remote.getIndicators())
            .thenThrow(exception)
    }

    private suspend fun givenSuccess() {
        val successResponse = ResponseUtil.indicatorList
        Mockito.`when`(netInfo.isOnline()).thenReturn(true)
        Mockito.`when`(remote.getIndicators())
            .thenReturn(Resource.Success(successResponse))
    }

    private fun givenOfflineConnection() {
        Mockito.`when`(netInfo.isOnline()).thenReturn(false)
    }

}
