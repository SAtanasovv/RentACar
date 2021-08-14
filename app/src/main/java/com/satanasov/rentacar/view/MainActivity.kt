package com.satanasov.rentacar.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.satanasov.rentacar.adapter.CarAdapter
import com.satanasov.rentacar.adapter.CarAdapterClickListener
import com.satanasov.rentacar.databinding.ActivityMainBinding
import com.satanasov.rentacar.dialogs.AddCarCustomDialog
import com.satanasov.rentacar.dialogs.HireCarCustomDialog
import com.satanasov.rentacar.models.CarModel
import com.satanasov.rentacar.presenter.MainActivityPresenter
import com.satanasov.rentacar.presenter.MainActivityView

class MainActivity : BaseActivity(), MainActivityView, CarAdapterClickListener {

    private lateinit var adapter    : CarAdapter
    private lateinit var binding    : ActivityMainBinding
    private lateinit var presenter  : MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter   = MainActivityPresenter(this)
        binding     = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
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
        binding.addFloatingButtonMainActivity.setOnClickListener { showAddCarDialogDialog() }
        setCarAdapter(arrayListOf())
    }

    private fun setCarAdapter(carModelList: ArrayList<CarModel>){
        adapter = CarAdapter(carModelList, this)
        binding.recyclerViewMainActivity.layoutManager  = LinearLayoutManager(this)
        binding.recyclerViewMainActivity.adapter        = adapter
    }

    private fun showAddCarDialogDialog(){
        val dialog = AddCarCustomDialog(this)
        dialog.setListener(object : AddCarCustomDialog.AddCarDialogListener {
            override fun onAddClicked(carModel: CarModel) {
                presenter.insertCar(carModel)
            }
        })
        dialog.showDialog()
    }

    private fun showHireCarDialog(){
        val dialog = HireCarCustomDialog(this)
        dialog.setListener(object : HireCarCustomDialog.HireCarDialogListener{
            override fun onHireClicked(minutes: Long) {
                var carModel                = presenter.currentCarToHire
                carModel.timeToBeRentedFor  = minutes
                carModel.currentTime        = System.currentTimeMillis()
                presenter.hireCar(carModel)
            }
        })
        dialog.showDialog()
    }

    override fun setAdapter(carModelList: ArrayList<CarModel>) {
        setCarAdapter(carModelList)
    }

    override fun updateList(carModelList: ArrayList<CarModel>) {
        adapter.updateList(carModelList)
    }

    override fun deleteCarModel(adapterPosition: Int) {
        adapter.deleteCarItem(adapterPosition)
    }

    override fun setNoCarsAddedText(show: Boolean) {
        if (show){
            binding.noCarsAdded.visibility                  = View.VISIBLE
            binding.recyclerViewMainActivity.visibility     = View.GONE
        }
        else{
            binding.noCarsAdded.visibility                  = View.GONE
            binding.recyclerViewMainActivity.visibility     = View.VISIBLE
        }
    }

    override fun onHireClicked(adapterPosition: Int, carModel: CarModel) {
        if(carModel.rentedTill != null && carModel.rentedTill!! != 0L && carModel.rentedTill!! > System.currentTimeMillis()){
            val timeLeft    = carModel.rentedTill!! - System.currentTimeMillis()
            val hours       = ((timeLeft/1000) / 60) / 60
            val minutes     = ((timeLeft/1000) / 60) % 60
            val seconds     = (timeLeft/1000) % 60

            Toast.makeText(this, "Can be rented in $hours Hours $minutes minutes $seconds seconds", Toast.LENGTH_SHORT).show()
        }
        else{
            showHireCarDialog()
            presenter.currentCarToHire = carModel
        }
    }

    override fun onDeleteClicked(adapterPosition: Int) {
        presenter.deleteCar(adapterPosition)
    }
}