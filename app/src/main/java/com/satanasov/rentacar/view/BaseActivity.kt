package com.satanasov.rentacar.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satanasov.rentacar.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}