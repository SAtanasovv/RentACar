package com.satanasov.rentacar.view

import android.os.Bundle
import android.widget.Toast
import com.satanasov.rentacar.R
import com.satanasov.rentacar.databinding.ActivityRegisterBinding
import com.satanasov.rentacar.models.UserModel
import com.satanasov.rentacar.presenter.RegisterActivityPresenter
import com.satanasov.rentacar.presenter.RegisterActivityView

class RegisterActivity : BaseActivity(), RegisterActivityView {

    private lateinit var  binding   : ActivityRegisterBinding
    private lateinit var  presenter : RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter   = RegisterActivityPresenter(this)
        binding     = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            if (validate())
                registerUser()
        }
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

    private fun clearFields(){
        binding.usernameEditText.setText("")
        binding.passwordEditText.setText("")
        binding.usernameEditText.clearFocus()
        binding.passwordEditText.clearFocus()
    }

    private fun registerUser(){
        presenter.insertUser(UserModel(
            userName = binding.usernameEditText.text.toString(),
            password = binding.passwordEditText.text.toString(),
            isAdmin  = binding.adminRadioButton.isChecked
        ))
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

    override fun goToLoginActivity() {
        Toast.makeText(this, getString(R.string.user_created), Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    override fun showToast() {
        Toast.makeText(this, getString(R.string.user_exist), Toast.LENGTH_SHORT).show()
    }
}