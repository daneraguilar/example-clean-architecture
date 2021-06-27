package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity

@Database(
    entities = [UserDaoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDB : RoomDatabase() {
    abstract fun counterDao(): UserDao
}