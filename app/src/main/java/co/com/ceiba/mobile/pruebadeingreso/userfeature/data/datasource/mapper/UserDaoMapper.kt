package co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.mapper

import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.AddressDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.CompanyDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.GeoDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Address
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Company
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Geo
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User

interface UserDaoMapperMapper {
    fun toUsersList(countersDao: List<UserDaoEntity>): List<User>

    fun toUsersDaoList(counters: List<User>): List<UserDaoEntity>
}

class UserDaoMapperMapperImpl : UserDaoMapperMapper {

    override fun toUsersList(countersDao: List<UserDaoEntity>) = countersDao.map {
        User(
            it.id,
            it.name,
            it.username,
            it.email,
            Address(
                it.address.street,
                it.address.suite,
                it.address.city,
                it.address.zipCode,
                Geo(it.address.geo.lat, it.address.geo.lng)
            ),
            it.phone,
            it.website,
            Company(it.company.companyName, it.company.catchPhrase, it.company.bs)
        )
    }

    override fun toUsersDaoList(counters: List<User>) = counters.map {
        UserDaoEntity(
            it.id,
            it.name,
            it.username,
            it.email,
            AddressDaoEntity(
                it.address.street,
                it.address.suite,
                it.address.city,
                it.address.zipCode,
                GeoDaoEntity(it.address.geo.lat, it.address.geo.lng)
            ),
            it.phone,
            it.website,
            CompanyDaoEntity(it.company.name, it.company.catchPhrase, it.company.bs)
        )
    }

}