package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.common.USER
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.adapter.UserAdapter
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel.ScreenState
import co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.viewmodel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val _userViewModel: UserViewModel by viewModel()
    private lateinit var _binder: ActivityMainBinding
    private lateinit var _adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binder = ActivityMainBinding.inflate(layoutInflater)

        setView()
        _userViewModel.model.observe(this, ::updateUI)
        setContentView(_binder.root)

    }

    private fun setView() {
        _adapter = UserAdapter {
            startActivity(Intent(this, PostActivity::class.java).putExtra(USER, it))
        }
        with(_binder) {
            recyclerViewSearchResults.adapter = _adapter
            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    print(s)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    print(s)
                }

                override fun afterTextChanged(s: Editable?) {
                    _adapter.filterUsers(s.toString())
                }

            })
        }
    }

    private fun updateUI(screenState: ScreenState) {

        when (screenState) {
            is ScreenState.Error -> showMessage(screenState.message)
            is ScreenState.HasUserContent -> _adapter.updateItems(screenState.list)
            is ScreenState.Loading -> showOrHideLoading(screenState.visible)
            is ScreenState.OffLineNetwork -> showMessage(getString(R.string.connection_error_description))
            ScreenState.NotContent -> showMessage(getString(R.string.no_results))
        }

    }

    private fun showMessage(string: String) {

    }

    private fun showOrHideLoading(visible: Boolean) {

    }

    override fun onStart() {
        super.onStart()
        _userViewModel.getUsers()
    }
}