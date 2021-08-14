package com.satanasov.rentacar.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.satanasov.rentacar.R
import com.satanasov.rentacar.databinding.CustomDialogAddCarBinding
import com.satanasov.rentacar.models.CarModel

class AddCarCustomDialog(context: Context) : Dialog(context) {
    private var dialogListener  : AddCarDialogListener? = null

    interface AddCarDialogListener{
        fun onAddClicked(carModel: CarModel)
    }

    fun setListener(listener: AddCarDialogListener){
        dialogListener = listener
    }

    fun showDialog(){
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            val binding = CustomDialogAddCarBinding.inflate(layoutInflater)
            setContentView(binding.root)
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

            binding.addCarCancelButton.setOnClickListener { dismiss() }
            binding.addCarAddButton.setOnClickListener {
                if( validate(binding) ){
                    dialogListener?.onAddClicked(CarModel(carModel = binding.carModelEditText.text.toString().trim(),registrationNumber = binding.regNumberEdit.text.toString().trim()))
                    dismiss()
                }
            }
            show()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun validate(binding: CustomDialogAddCarBinding) : Boolean{
        var valid = true

         if (binding.carModelEditText.text.toString().isEmpty()) {
             binding.carModelEditText.error = context.getString(R.string.please_enter_model)
             valid = false
         }

         if (binding.regNumberEdit.text.toString().isEmpty()) {
             binding.regNumberEdit.error = context.getString(R.string.please_enter_registration_number)
             valid = false
         }
        return valid
    }
}