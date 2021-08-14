package com.satanasov.rentacar.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.satanasov.rentacar.R
import com.satanasov.rentacar.databinding.ActivityLoginBinding
import com.satanasov.rentacar.globalData.Utils
import com.satanasov.rentacar.presenter.LoginActivityPresenter
import com.satanasov.rentacar.presenter.LoginActivityView

class LoginActivity : BaseActivity(), LoginActivityView {

    private lateinit var  binding   : ActivityLoginBinding
    private lateinit var  presenter : LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter   = LoginActivityPresenter(this)
        binding     = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        clearFields()
    }

    override fun onPause() {
        presenter.unSubscribe()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun init(){
        binding.loginButton.setOnClickListener {
           if (validate())
               presenter.onLoginClicked(binding.usernameEditText.text.toString().trim(), binding.passwordEditText.text.toString().trim())
        }
        binding.registerButton.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }

    private fun clearFields(){
        binding.usernameEditText.setText("")
        binding.passwordEditText.setText("")
        binding.usernameEditText.clearFocus()
        binding.passwordEditText.clearFocus()
    }

    private fun validate() : Boolean{
        var valid = true

        if (binding.usernameEditText.text.toString().isNotEmpty() && binding.usernameEditText.text.length < 5) {
            binding.usernameEditText.error = getString(R.string.please_enter_characters)
            valid = false
        }

        if (binding.passwordEditText.text.toString().isNotEmpty() && binding.passwordEditText.text.length < 5) {
            binding.passwordEditText.error = getString(R.string.please_enter_characters)
            valid = false
        }

        if (binding.usernameEditText.text.toString().isEmpty()) {
            binding.usernameEditText.error = getString(R.string.please_enter_username)
            valid = false
        }

        if (binding.passwordEditText.text.toString().isEmpty()) {
            binding.passwordEditText.error = getString(R.string.please_enter_password)
            valid = false
        }

        return valid
    }

    override fun noUsersFound(show: Boolean) {
        if (show)
            binding.loginButton.visibility = View.GONE
        else
            binding.loginButton.visibility = View.VISIBLE
    }

    override fun goToMainActivity(isAdmin: Boolean) {
        startActivity(Intent(this, MainActivity::class.java).apply { putExtra(Utils.INTENT_TRANSFER_IS_ADMIN, isAdmin) })
    }

    override fun showErrorToast() {
        Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
    }

}