package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain

import co.com.ceiba.mobile.pruebadeingreso.common.DEFAULT_ERROR_MESSAGE
import co.com.ceiba.mobile.pruebadeingreso.common.Result

class GetUserPostsUseCase(private val _repository: UserRepository) {


    suspend operator fun invoke(userId: Int): UserPostResultUseCase {

        return when (val result = _repository.getUserPosts(userId)) {

            is Result.Success -> Result.Success(result.value)

            is Result.Failure -> when (result.reason) {
                UserResultRepositoryError.NetworkError -> Result.Failure(UserResultUseCaseError.NetworkError)
                else -> Result.Failure(UserResultUseCaseError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }


        }
    }
}