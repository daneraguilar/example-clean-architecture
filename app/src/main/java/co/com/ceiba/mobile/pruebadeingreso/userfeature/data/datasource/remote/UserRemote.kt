package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote

import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserPostResultRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepository

interface UserRemote {

    suspend fun getUsers(): UserResultRepository
    suspend fun getUserPosts(userId: Int): UserPostResultRepository
}