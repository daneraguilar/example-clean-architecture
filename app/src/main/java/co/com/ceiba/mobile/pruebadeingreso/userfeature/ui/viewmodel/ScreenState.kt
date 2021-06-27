package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel

import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Post
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User

sealed class ScreenState {
    data class Loading(val visible: Boolean) : ScreenState()
    data class HasPostContent(val list: List<Post>) : ScreenState()
    data class HasUserContent(val list: List<User>) : ScreenState()
    object NotContent : ScreenState()
    data class Error(val message: String) : ScreenState()
    object OffLineNetwork : ScreenState()
}


