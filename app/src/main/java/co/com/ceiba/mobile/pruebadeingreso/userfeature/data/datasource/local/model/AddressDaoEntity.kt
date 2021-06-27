package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model

import androidx.room.Embedded

class AddressDaoEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String,
    @Embedded val geo: GeoDaoEntity
)