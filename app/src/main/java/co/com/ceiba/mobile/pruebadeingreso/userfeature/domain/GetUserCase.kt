package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain

import co.com.ceiba.mobile.pruebadeingreso.common.DEFAULT_ERROR_MESSAGE
import co.com.ceiba.mobile.pruebadeingreso.common.Result

class GetUserCase(private val _repository: UserRepository) {


    suspend operator fun invoke(): UserResultUseCase {

        return when (val result = _repository.getUsers()) {

            is Result.Success -> Result.Success(result.value)

            is Result.Failure -> when (result.reason) {
                UserResultRepositoryError.NetworkError -> Result.Failure(UserResultUseCaseError.NetworkError)
                else -> Result.Failure(UserResultUseCaseError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }


        }
    }
}