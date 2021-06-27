package co.com.ceiba.mobile.pruebadeingreso.userfeature.data

import co.com.ceiba.mobile.pruebadeingreso.common.NetworkManagerState
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.UserDao
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.mapper.UserDaoMapperMapper
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote.UserRemote
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserPostResultRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepositoryError

class UserRepositoryImpl(
    private val _userLocal: UserDao,
    private val _userRemote: UserRemote,
    private val _networkManagerState: NetworkManagerState,
    private val _mapper: UserDaoMapperMapper

) : UserRepository {
    override suspend fun getUsers(): UserResultRepository {


        val resultDao = _userLocal.getAll()
        return when {

            resultDao.isNotEmpty() -> Result.Success(_mapper.toUsersList(resultDao))

            _networkManagerState.isConnected() -> {
                val resultRemote = _userRemote.getUsers()
                if (resultRemote is Result.Success) _userLocal.insertList(
                    _mapper.toUsersDaoList(
                        resultRemote.value
                    )
                )
                resultRemote
            }
            else -> Result.Failure(UserResultRepositoryError.NetworkError)
        }
    }

    override suspend fun getUserPosts(userId: Int): UserPostResultRepository {
        return when {
            _networkManagerState.isConnected() -> _userRemote.getUserPosts(userId)
            else -> Result.Failure(UserResultRepositoryError.NetworkError)
        }
    }
}