package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.common.USER
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.adapter.PostAdapter
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel.ScreenState
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity : AppCompatActivity() {

    private val _userViewModel: UserViewModel by viewModel()
    private lateinit var _binder: ActivityPostBinding
    private lateinit var _adapter: PostAdapter
    private lateinit var _user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binder = ActivityPostBinding.inflate(layoutInflater)
        _userViewModel.model.observe(this, ::updateUI)
        intent?.getParcelableExtra<User>(USER)?.let { _user = it }
        setView()
        setContentView(_binder.root)
    }

    override fun onStart() {
        super.onStart()
        _userViewModel.getPosts(_user.id)
    }

    private fun setView() {
        _adapter = PostAdapter()
        with(_binder) {
            name.text = _user.name
            phone.text = _user.phone
            email.text = _user.email
            recyclerViewPostsResults.adapter = _adapter
        }
    }

    private fun updateUI(screenState: ScreenState) {

        when (screenState) {
            is ScreenState.Error -> showMessage(screenState.message)
            is ScreenState.HasPostContent -> _adapter.updateItems(screenState.list)
            is ScreenState.Loading -> showOrHideLoading(screenState.visible)
            is ScreenState.OffLineNetwork -> showMessage(getString(R.string.connection_error_description))
            ScreenState.NotContent -> showMessage(getString(R.string.no_results))
        }

    }

    private fun showMessage(string: String) {

    }

    private fun showOrHideLoading(visible: Boolean) {

    }
}