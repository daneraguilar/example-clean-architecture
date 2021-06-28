package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote

import co.com.ceiba.mobile.pruebadeingreso.common.DEFAULT_ERROR_MESSAGE
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserPostResultRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepositoryError

class UserRemoteImpl(private val _retrofit: UserRemoteRetrofit) : UserRemote {
    override suspend fun getUsers(): UserResultRepository {
        try {
            val result = _retrofit.getUsers()

            return when (result.code()) {
                HttpCode.OK.code -> {
                    result.body()?.let { Result.Success(it) } ?: kotlin.run {
                        Result.Failure(
                            UserResultRepositoryError.ParserError
                        )
                    }
                }
                HttpCode.BAD_REQUEST.code -> Result.Failure(
                    UserResultRepositoryError.BadRequest(
                        result.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
                    )
                )
                HttpCode.UN_AUTHORIZED.code -> Result.Failure(UserResultRepositoryError.UnAuthorized)
                else -> Result.Failure(UserResultRepositoryError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            return Result.Failure(
                UserResultRepositoryError.UnKnowError(
                    e.localizedMessage ?: DEFAULT_ERROR_MESSAGE
                )
            )
        }
    }

    override suspend fun getUserPosts(userId: Int): UserPostResultRepository {
        try {
            val result = _retrofit.getUserPost(userId)

            return when (result.code()) {
                HttpCode.OK.code -> {
                    result.body()?.let { Result.Success(it) } ?: kotlin.run {
                        Result.Failure(
                            UserResultRepositoryError.ParserError
                        )
                    }
                }
                HttpCode.BAD_REQUEST.code -> Result.Failure(
                    UserResultRepositoryError.BadRequest(
                        result.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
                    )
                )
                HttpCode.UN_AUTHORIZED.code -> Result.Failure(UserResultRepositoryError.UnAuthorized)
                else -> Result.Failure(UserResultRepositoryError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            return Result.Failure(
                UserResultRepositoryError.UnKnowError(
                    e.localizedMessage ?: DEFAULT_ERROR_MESSAGE
                )
            )
        }
    }
}