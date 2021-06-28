package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.com.ceiba.mobile.pruebadeingreso.MainCoroutineRule
import co.com.ceiba.mobile.pruebadeingreso.UserTestFactory
import co.com.ceiba.mobile.pruebadeingreso.common.DEFAULT_ERROR_MESSAGE
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserPostsUseCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultUseCaseError
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest : TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    private lateinit var _getUsers: GetUserCase

    @Mock
    private lateinit var _getUserPostsUserCase: GetUserPostsUseCase

    @Mock
    private lateinit var _observer: Observer<ScreenState>

    private lateinit var _userViewModel: UserViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        _userViewModel = UserViewModel(
            _getUsers,
            _getUserPostsUserCase, Dispatchers.Main
        )
    }

    @Test
    fun `when  get users with Success response then  change state to has content`() {
        runBlocking {

            val list = UserTestFactory.createListUsers()
            Mockito.`when`(_getUsers()).thenReturn(Result.Success(list))
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getUsers()

            Mockito.verify(_observer).onChanged(ScreenState.HasUserContent(list))

        }
    }

    @Test
    fun `when  get users with network exception response then  change state to offline network`() {
        runBlocking {
            Mockito.`when`(_getUsers())
                .thenReturn(Result.Failure(UserResultUseCaseError.NetworkError))
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getUsers()

            Mockito.verify(_observer).onChanged(ScreenState.OffLineNetwork)

        }
    }

    @Test
    fun `when  get users with un know error response then  change state un know error`() {
        runBlocking {
            Mockito.`when`(_getUsers()).thenReturn(
                Result.Failure(
                    UserResultUseCaseError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getUsers()

            Mockito.verify(_observer).onChanged(ScreenState.Error(DEFAULT_ERROR_MESSAGE))

        }
    }

    @Test
    fun `when  get posts with Success response then  change state to has content`() {
        runBlocking {
            val id = 1
            val list = UserTestFactory.createListPosts()
            Mockito.`when`(_getUserPostsUserCase(id)).thenReturn(Result.Success(list))
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getPosts(id)

            Mockito.verify(_observer).onChanged(ScreenState.HasPostContent(list))

        }
    }

    @Test
    fun `when  get posts with network exception response then  change state to offline network`() {
        runBlocking {
            val id = 1
            Mockito.`when`(_getUserPostsUserCase(id))
                .thenReturn(Result.Failure(UserResultUseCaseError.NetworkError))
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getPosts(id)

            Mockito.verify(_observer).onChanged(ScreenState.OffLineNetwork)

        }
    }

    @Test
    fun `when  get posts with un know error response then  change state un know error`() {
        runBlocking {
            val id = 1
            Mockito.`when`(_getUserPostsUserCase(id)).thenReturn(
                Result.Failure(
                    UserResultUseCaseError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )
            _userViewModel.model.observeForever(_observer)

            _userViewModel.getPosts(id)

            Mockito.verify(_observer).onChanged(ScreenState.Error(DEFAULT_ERROR_MESSAGE))

        }
    }
}