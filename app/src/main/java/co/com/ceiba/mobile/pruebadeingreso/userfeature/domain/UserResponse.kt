package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain

import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Post
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User

class UserResponse {
}

typealias  UserResultUseCase = Result<List<User>, UserResultUseCaseError>
typealias  UserPostResultUseCase = Result<List<Post>, UserResultUseCaseError>
typealias  UserPostResultRepository = Result<List<Post>, UserResultRepositoryError>
typealias  UserResultRepository = Result<List<User>, UserResultRepositoryError>


sealed class UserResultUseCaseError {
    data class UnKnowError(val message: String) : UserResultUseCaseError()
    object NetworkError : UserResultUseCaseError()
}

sealed class UserResultRepositoryError {

    object NetworkError : UserResultRepositoryError()
    object UnAuthorized : UserResultRepositoryError()
    data class BadRequest(val message: String) : UserResultRepositoryError()
    data class UnKnowError(val message: String) : UserResultRepositoryError()
    object ParserError : UserResultRepositoryError()
}