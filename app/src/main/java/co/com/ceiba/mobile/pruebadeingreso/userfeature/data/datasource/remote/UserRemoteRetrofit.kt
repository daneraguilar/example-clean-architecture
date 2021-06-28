package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote

import co.com.ceiba.mobile.pruebadeingreso.common.GET_POST_USER
import co.com.ceiba.mobile.pruebadeingreso.common.GET_USERS
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Post
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserRemoteRetrofit {

    @GET(GET_USERS)
    suspend fun getUsers(): Response<List<User>>

    @GET(GET_POST_USER)
    suspend fun getUserPost(@Query("userId") userId: Int): Response<List<Post>>
}