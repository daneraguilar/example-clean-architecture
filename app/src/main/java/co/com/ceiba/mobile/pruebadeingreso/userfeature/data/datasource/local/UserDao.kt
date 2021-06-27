package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<UserDaoEntity>

    @Insert
    suspend fun insertList(usersDao: List<UserDaoEntity>)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}