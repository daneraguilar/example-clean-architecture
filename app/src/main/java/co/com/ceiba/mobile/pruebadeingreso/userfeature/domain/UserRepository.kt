package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain

interface UserRepository {

    suspend fun getUsers(): UserResultRepository
    suspend fun getUserPosts(userId: Int): UserPostResultRepository
}