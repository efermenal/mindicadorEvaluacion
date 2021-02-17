package com.example.mindicadorevaluation.features.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mindicadorevaluation.core.crypto.*
import com.example.mindicadorevaluation.core.models.User
import com.example.mindicadorevaluation.core.services.Authenticator
import com.example.mindicadorevaluation.core.utils.ResourceLogin
import com.example.mindicadorevaluation.db.UserDao
import com.example.mindicadorevaluation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainActivityViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private
    lateinit var  viewModel: MainActivityViewModel

    private
    val encryption = Mockito.mock(Encryption::class.java)

    private
    lateinit var base64Test : Base64Cipher

    private
    val keys = Mockito.mock(KeysRepository::class.java)

    private
    val userDao = Mockito.mock(UserDao::class.java)

    private
   val auth = Mockito.mock(Authenticator::class.java)



    @ExperimentalCoroutinesApi
    @Before
    fun init(){
        base64Test = Base64CipherTesting()
        Mockito.`when`(keys.getSecretKey()).thenReturn("estoesunasuperasswordparaprobarl")
       // encryption = EncryptionAES(keys, base64Test)

        viewModel = MainActivityViewModel(encryption, userDao, Dispatchers.Unconfined, auth)

        Dispatchers.setMain(dispatcher)
    }


    @Test
    fun attemptingLogin_credentialInvalids_returnsInvalid() = runBlockingTest {
        val user = "endherson"
        val pass = "eddd"
        val list = flowOf(listOf(User(user, "a")))

        Mockito.`when`(userDao.getUserById(user)).thenReturn(list)
        Mockito.`when`(encryption.encode(pass)).thenReturn("pass")
        viewModel.attemptingLogin(user, pass)

        val value = viewModel.isOn.getOrAwaitValue()


        MatcherAssert.assertThat(value, IsInstanceOf.instanceOf(ResourceLogin.Invalid::class.java))
    }

    @Test
    fun attemptingLogin_credentialValids_returnsValid() = runBlockingTest {
        val user = "endherson"
        val pass = "endherson"
        val list = flowOf(listOf(User(user, pass)))

        Mockito.`when`(userDao.getUserById(user)).thenReturn(list)
        Mockito.`when`(encryption.encode(pass)).thenReturn(pass)
        viewModel.attemptingLogin(user, pass)

        val value = viewModel.isOn.getOrAwaitValue()

        MatcherAssert.assertThat(value, IsInstanceOf.instanceOf(ResourceLogin.Valid::class.java))
    }



}