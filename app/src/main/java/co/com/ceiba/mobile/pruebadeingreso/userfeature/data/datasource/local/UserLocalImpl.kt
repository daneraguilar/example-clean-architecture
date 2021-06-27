package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local

import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity

class UserLocalImpl(private val _database: UserDB) : UserDao {
    override fun getAll(): List<UserDaoEntity> = _database.counterDao().getAll()


    override suspend fun insertList(usersDao: List<UserDaoEntity>) {
        deleteAllUsers()
        _database.counterDao().insertList(usersDao)
    }

    override suspend fun deleteAllUsers() {
        _database.counterDao().deleteAllUsers()
    }

}