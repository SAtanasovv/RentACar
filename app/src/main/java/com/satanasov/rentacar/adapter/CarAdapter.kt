package com.satanasov.rentacar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satanasov.rentacar.R
import com.satanasov.rentacar.models.CarModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.car_row.view.*

class CarAdapter(
    private val carList: ArrayList<CarModel>
) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.car_row,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bindItem(position) }

    override fun getItemCount() = carList.size

    inner class ViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bindItem(position: Int){
            with(carList[position]){
                containerView.modelTextView.text     = carModel
                containerView.regNumberTextView.text = registrationNumber

                containerView.hireCarButton.setOnClickListener {

                }

                containerView.removeCarButton.setOnClickListener {


                }
            }
        }
    }
}