package com.satanasov.rentacar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satanasov.rentacar.databinding.CarRowBinding
import com.satanasov.rentacar.models.CarModel

class CarAdapter(private var carList: ArrayList<CarModel>, private val listener: CarAdapterClickListener) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(CarRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bindItem(position) }

    override fun getItemCount() = carList.size

    fun updateList(carModelList: ArrayList<CarModel>){
        carList.clear()
        carList.addAll(carModelList)
        this.notifyDataSetChanged()
    }

    fun deleteCarItem(adapterPosition: Int){
        carList.removeAt(adapterPosition)
        this.notifyItemRemoved(adapterPosition)
    }

    inner class ViewHolder(private val containerView : CarRowBinding) : RecyclerView.ViewHolder(containerView.root){
        fun bindItem(position: Int){
            with(carList[position]){
                containerView.modelTextView.text     = carModel
                containerView.regNumberTextView.text = registrationNumber

                containerView.hireCarButton.setOnClickListener {
                    listener.onHireClicked(adapterPosition, carList[adapterPosition])
                }
                containerView.removeCarButton.setOnClickListener {
                    listener.onDeleteClicked(adapterPosition)
                }
            }
        }
    }
}

interface CarAdapterClickListener{
    fun onHireClicked(adapterPosition: Int, carModel: CarModel)
    fun onDeleteClicked(adapterPosition: Int)
}