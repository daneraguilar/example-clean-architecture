package co.com.ceiba.mobile.pruebadeingreso

import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.AddressDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.CompanyDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.GeoDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.model.UserDaoEntity
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.*
import retrofit2.Response

class UserTestFactory {

    companion object {
        fun createListUsers(): List<User> = mutableListOf(
            createUsers(id = 1),
            createUsers(id = 2),
            createUsers(id = 3),
            createUsers(id = 4),
            createUsers(id = 5)
        )

        fun createListDaoUser(): List<UserDaoEntity> = mutableListOf(
            createDaoUser(id = 1),
            createDaoUser(id = 2),
            createDaoUser(id = 3),
            createDaoUser(id = 4),
            createDaoUser(id = 5)
        )

        fun createListPosts(): List<Post> = mutableListOf(
            createPost(userId = 1),
            createPost(userId = 1),
            createPost(userId = 2),
            createPost(userId = 3),
            createPost(userId = 5),
        )

        private fun createPost(
            userId: Int = 1,
            id: String = "",
            title: String = "",
            body: String = ""
        ): Post = Post(userId, id, title, body)

        private fun createUsers(
            id: Int = 1,
            name: String = "",
            username: String = "",
            email: String = "",
            phone: String = "",
            website: String = ""
        ): User =
            User(id, name, username, email, createAddress(), phone, website, createCompany())

        private fun createDaoUser(
            id: Int = 1,
            name: String = "",
            username: String = "",
            email: String = "",
            phone: String = "",
            website: String = ""
        ): UserDaoEntity =
            UserDaoEntity(
                id,
                name,
                username,
                email,
                createDaoAddress(),
                phone,
                website,
                createDaoCompany()
            )

        private fun createAddress(): Address = Address("", "", "", "", createGeo())
        private fun createDaoAddress(): AddressDaoEntity =
            AddressDaoEntity("", "", "", "", createGeoDao())

        private fun createGeo(lat: String = "10.65855", lng: String = "-73.555845"): Geo =
            Geo(lat, lng)

        private fun createGeoDao(
            lat: String = "10.65855",
            lng: String = "-73.555845"
        ): GeoDaoEntity =
            GeoDaoEntity(lat, lng)

        private fun createCompany(
            name: String = "",
            catchPhrase: String = "",
            bs: String = ""
        ): Company = Company(name, catchPhrase, bs)

        private fun createDaoCompany(
            name: String = "",
            catchPhrase: String = "",
            bs: String = ""
        ): CompanyDaoEntity = CompanyDaoEntity(name, catchPhrase, bs)

        fun createSuccessResponseCounter(code: Int): Response<List<User>> =
            Response.success(code, createListUsers())
    }
}