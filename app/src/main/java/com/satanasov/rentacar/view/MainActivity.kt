package com.satanasov.rentacar.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.satanasov.rentacar.R
import com.satanasov.rentacar.adapter.CarAdapter
import com.satanasov.rentacar.dialogs.AddCarCustomDialog
import com.satanasov.rentacar.models.CarModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var adapter    : CarAdapter
    private lateinit var dialog     : AddCarCustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        adapter = CarAdapter(arrayListOf(CarModel("mercedes", "1111"), CarModel("bmw", "2222"), CarModel("opel", "3333")))
        recyclerViewMainActivity.layoutManager  = LinearLayoutManager(this)
        recyclerViewMainActivity.adapter        = adapter
        addFloatingButtonMainActivity.setOnClickListener { showAddCarDialog() }
    }

    private fun showAddCarDialog(){
        dialog = AddCarCustomDialog(this)
        dialog.setListener(object : AddCarCustomDialog.AddCarDialogListener{
            override fun onCancelClicked() {}

            override fun onAddClicked(carModel: CarModel) {}

        })
        dialog.showDialog()
    }
}