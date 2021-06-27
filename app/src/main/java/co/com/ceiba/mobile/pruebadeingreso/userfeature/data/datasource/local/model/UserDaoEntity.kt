package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserDaoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    @Embedded val address: AddressDaoEntity,
    val phone: String,
    val website: String,
    @Embedded val company: CompanyDaoEntity
)