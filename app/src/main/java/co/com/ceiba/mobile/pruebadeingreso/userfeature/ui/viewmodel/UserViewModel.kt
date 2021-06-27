package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.common.Result
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.GetUserPostsUseCase
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.UserResultUseCaseError
import kotlinx.coroutines.*

class UserViewModel(
    private val _getUserCase: GetUserCase,
    private val _getUserPostUserCase: GetUserPostsUseCase,
    private val _dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {


    private val _model = MutableLiveData<ScreenState>()
    private var _job: Job? = null

    val model: LiveData<ScreenState>
        get() {
            if (_model.value == null) _model.value = ScreenState.Loading(true)
            return _model
        }

    fun getUsers() {
        _job?.cancel()
        _job = viewModelScope.launch(_dispatcher) {
            when (val result = _getUserCase()) {

                is Result.Success -> withContext(Dispatchers.Main) {
                    _model.value = ScreenState.Loading(false)
                    _model.value = ScreenState.HasUserContent(result.value)
                }
                is Result.Failure -> {
                    when (result.reason) {
                        is UserResultUseCaseError.NetworkError -> withContext(Dispatchers.Main) {
                            _model.value = ScreenState.Loading(false)
                            _model.value = ScreenState.OffLineNetwork
                        }
                        is UserResultUseCaseError.UnKnowError -> withContext(Dispatchers.Main) {
                            _model.value = ScreenState.Loading(false)
                            _model.value = ScreenState.Error(result.reason.message)
                        }
                    }

                }
            }

        }

    }

    fun getPosts(userId: Int) {
        _job?.cancel()
        _job = viewModelScope.launch(_dispatcher) {
            when (val result = _getUserPostUserCase(userId)) {

                is Result.Success -> withContext(Dispatchers.Main) {
                    _model.value = ScreenState.Loading(false)
                    _model.value = ScreenState.HasPostContent(result.value)
                }
                is Result.Failure -> {
                    when (result.reason) {
                        is UserResultUseCaseError.NetworkError -> withContext(Dispatchers.Main) {
                            _model.value = ScreenState.Loading(false)
                            _model.value = ScreenState.OffLineNetwork
                        }
                        is UserResultUseCaseError.UnKnowError -> withContext(Dispatchers.Main) {
                            _model.value = ScreenState.Loading(false)
                            _model.value = ScreenState.Error(result.reason.message)
                        }
                    }

                }
            }

        }

    }
}