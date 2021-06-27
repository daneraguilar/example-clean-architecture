package co.com.ceiba.mobile.pruebadeingreso

import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.*

class UserTestFactory {

    companion object {
        fun createListUsers(): List<User> = mutableListOf(
            createUsers(id = 1),
            createUsers(id = 2),
            createUsers(id = 3),
            createUsers(id = 4),
            createUsers(id = 5)
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

        private fun createAddress(): Address = Address("", "", "", "", createGeo())

        private fun createGeo(lat: String = "10.65855", lng: String = "-73.555845"): Geo =
            Geo(lat, lng)

        private fun createCompany(
            name: String = "",
            catchPhrase: String = "",
            bs: String = ""
        ): Company = Company(name, catchPhrase, bs)


    }
}