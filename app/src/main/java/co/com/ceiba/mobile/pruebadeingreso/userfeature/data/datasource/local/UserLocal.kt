package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local

import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultRepository

interface UserLocal {
    suspend fun getUsers(): UserResultRepository
    suspend fun insertList(usersDao: List<UserDaoEntity>)
}