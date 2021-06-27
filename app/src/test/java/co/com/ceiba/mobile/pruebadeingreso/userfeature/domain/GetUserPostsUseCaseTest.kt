package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain

import co.com.ceiba.mobile.pruebadeingreso.UserTestFactory
import co.com.ceiba.mobile.pruebadeingreso.common.DEFAULT_ERROR_MESSAGE
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserPostsUseCaseTest : TestCase() {

    @Mock
    private lateinit var _repository: UserRepository

    @InjectMocks
    private lateinit var _getUserPostsUserCase: GetUserPostsUseCase

    @Test
    fun `when do get posts  with Success response then  return List of 5 users`() {
        runBlocking {

            val expect = 5
            val list = UserTestFactory.createListPosts()
            Mockito.`when`(_repository.getUserPosts(1)).thenReturn(Result.Success(list))
            when (val result = _getUserPostsUserCase(1)) {
                is Result.Success -> {
                    assert(result.value.size == expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when do get posts with failure network response then  return network exception`() {
        runBlocking {

            Mockito.`when`(_repository.getUserPosts(1)).thenReturn(
                Result.Failure(
                    UserResultRepositoryError.NetworkError
                )
            )

            when (val result = _getUserPostsUserCase(1)) {

                is Result.Success -> assert(false)
                is Result.Failure -> assert(result.reason is UserResultUseCaseError.NetworkError)
            }

        }
    }

    @Test
    fun `when do get posts  with failure un controller error response then  return un know error exception`() {
        runBlocking {
            Mockito.`when`(_repository.getUserPosts(1)).thenReturn(
                Result.Failure(
                    UserResultRepositoryError.UnKnowError(DEFAULT_ERROR_MESSAGE)
                )
            )
            when (val result = _getUserPostsUserCase(1)) {

                is Result.Success -> assert(false)
                is Result.Failure -> assert(result.reason is UserResultUseCaseError.UnKnowError)
            }

        }
    }
}