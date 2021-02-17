package com.example.mindicadorevaluation.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.services.NetworkInformation
import com.example.mindicadorevaluation.core.services.RemoteRepository
import com.example.mindicadorevaluation.core.utils.Resource
import com.example.mindicadorevaluation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response


class DetailViewModelTest {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private
    lateinit var  viewModel: DetailViewModel

    private
    val auth = Mockito.mock(Authenticator::class.java)

    private
    val remote = Mockito.mock(RemoteRepository::class.java)

    private
    val netInfo = Mockito.mock(NetworkInformation::class.java)

    @Before
    fun init(){
        viewModel = DetailViewModel(remote,netInfo,auth)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun getIndicators_whenIsOffline_return_NoInternet() = runBlockingTest {
        Mockito.`when`(netInfo.isOnline()).thenReturn(false)

        viewModel.getIndicators()

        val value = viewModel.responseApi.getOrAwaitValue()

        MatcherAssert.assertThat(value, IsInstanceOf.instanceOf(Resource.NoInternet::class.java))
    }

    @Test
    fun getIndicators_whenIsOnLine_return_Success() = runBlockingTest {

        val response = ResponseUtil()
        Mockito.`when`(netInfo.isOnline()).thenReturn(true)
        Mockito.`when`(remote.getIndicators()).thenReturn(Resource.Success(response.getExampleIndicatorResponse()))

        viewModel.getIndicators()

        val value = viewModel.responseApi.getOrAwaitValue()

        MatcherAssert.assertThat(value, IsInstanceOf.instanceOf(Resource.Success::class.java))
    }


}