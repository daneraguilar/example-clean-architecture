package co.com.ceiba.mobile.pruebadeingreso.userfeature.di

import android.app.Application
import androidx.room.Room
import co.com.ceiba.mobile.pruebadeingreso.common.NetworkManagerState
import co.com.ceiba.mobile.pruebadeingreso.common.NetworkManagerStateImpl
import co.com.ceiba.mobile.pruebadeingreso.common.URL_BASE
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.UserRepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.UserDB
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.UserDao
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.local.UserLocalImpl
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.mapper.UserDaoMapperMapper
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.mapper.UserDaoMapperMapperImpl
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote.UserRemote
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote.UserRemoteImpl
import co.com.ceiba.mobile.pruebadeingreso.userfeature.data.datasource.remote.UserRemoteRetrofit
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserPostsUseCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun Application.initDI() {

    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(UserModule))
    }

}

val UserModule = module {
    factory<UserRemoteRetrofit> {
        val okHttpClient =
            OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build()
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserRemoteRetrofit::class.java)
    }
    single<UserDB> {
        Room.databaseBuilder(
            androidApplication(),
            UserDB::class.java, "users"
        ).build()
    }
    single<UserDao> { UserLocalImpl(get()) }
    single<UserDaoMapperMapper> { UserDaoMapperMapperImpl() }
    single<NetworkManagerState> { NetworkManagerStateImpl(androidContext()) }
    single<UserRemote> { UserRemoteImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get(), get()) }

    single { GetUserCase(get()) }
    single { GetUserPostsUseCase(get()) }
    viewModel { UserViewModel(get(), get()) }
}
