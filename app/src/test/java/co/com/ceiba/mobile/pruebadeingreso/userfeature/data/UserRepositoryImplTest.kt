package co.com.ceiba.mobile.pruebadeingreso.userfeature.data

import co.com.ceiba.mobile.pruebadeingreso.UserTestFactory
import co.com.ceiba.mobile.pruebadeingreso.common.NetworkManagerState
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.UserDao
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.mapper.UserDaoMapperMapper
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote.UserRemote
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepositoryError
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest : TestCase() {

    @Mock
    private lateinit var _userLocal: UserDao

    @Mock
    private lateinit var _userRemote: UserRemote

    @Mock
    private lateinit var _networkManagerState: NetworkManagerState

    @Mock
    private lateinit var _mapper: UserDaoMapperMapper

    @InjectMocks
    private lateinit var _userRepository: UserRepositoryImpl

    @Test
    fun `when do get users  then  return List of user with size 5`() {
        runBlocking {

            val expect = 5
            val list = UserTestFactory.createListUsers()
            Mockito.`when`(_userLocal.getAll()).thenReturn(mutableListOf())
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(true)
            Mockito.`when`(_userRemote.getUsers()).thenReturn(Result.Success(list))

            when (val result = _userRepository.getUsers()) {

                is Result.Success -> {
                    assertEquals(result.value.size, expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when do get users  with offline mode return network error`() {
        runBlocking {

            val list = UserTestFactory.createListUsers()

            Mockito.`when`(_userLocal.getAll()).thenReturn(mutableListOf())
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(false)

            when (val result = _userRepository.getUsers()) {

                is Result.Success -> assert(false)
                is Result.Failure -> assert(result.reason is UserResultRepositoryError.NetworkError)
            }

        }
    }

    @Test
    fun `when do get users  from local  return list with size 5`() {
        runBlocking {
            val expect = 5
            val listUserDao = UserTestFactory.createListDaoUser()
            val listUser = UserTestFactory.createListUsers()

            Mockito.`when`(_userLocal.getAll()).thenReturn(listUserDao)
            Mockito.`when`(_mapper.toUsersList(listUserDao)).thenReturn(listUser)

            when (val result = _userRepository.getUsers()) {

                is Result.Success -> assertEquals(result.value.size, expect)
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when do get user post  with offline mode return network error`() {
        runBlocking {
            val id = 4

            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(false)

            when (val result = _userRepository.getUserPosts(id)) {

                is Result.Success -> assert(false)
                is Result.Failure -> assert(result.reason is UserResultRepositoryError.NetworkError)
            }

        }
    }

    @Test
    fun `when do get users  then  return List of post with size 5`() {
        runBlocking {

            val expect = 5
            val list = UserTestFactory.createListPosts()
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(true)
            Mockito.`when`(_userRemote.getUserPosts(1)).thenReturn(Result.Success(list))

            when (val result = _userRepository.getUserPosts(1)) {

                is Result.Success -> {
                    assertEquals(result.value.size, expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }
}
