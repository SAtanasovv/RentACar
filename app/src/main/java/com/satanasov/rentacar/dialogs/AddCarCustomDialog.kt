package com.satanasov.rentacar.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.satanasov.rentacar.R
import com.satanasov.rentacar.databinding.CustomDialogAddCarBinding
import com.satanasov.rentacar.models.CarModel

class AddCarCustomDialog(context: Context) : Dialog(context) {
    private var dialogListener  : AddCarDialogListener? = null

    interface AddCarDialogListener{
        fun onHireClicked(minutes: Long)
        fun onAddClicked(carModel: CarModel)
    }

    fun setListener(listener: AddCarDialogListener){
        dialogListener = listener
    }

    fun showDialog(isForHire: Boolean){
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            val binding = CustomDialogAddCarBinding.inflate(layoutInflater)
            setContentView(binding.root)
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

            binding.addCarCancelButton.setOnClickListener {
                dismiss()
            }

            binding.addCarAddButton.setOnClickListener {
                if( validate(binding, isForHire) ){
                    if (isForHire)
                        dialogListener?.onHireClicked(binding.minutesEditText.text.toString().toLong())
                    else
                        dialogListener?.onAddClicked(CarModel(carModel = binding.carModelEditText.text.toString(),registrationNumber = binding.regNumberEdit.text.toString()))
                    dismiss()
                }
            }

            if (isForHire){
                binding.regNumberLayout.visibility          = View.GONE
                binding.carModelTxtInputLayout.visibility   = View.GONE
                binding.minutesTxtInputLayout.visibility    = View.VISIBLE
            }
            else{
                binding.regNumberLayout.visibility          = View.VISIBLE
                binding.carModelTxtInputLayout.visibility   = View.VISIBLE
                binding.minutesTxtInputLayout.visibility    = View.GONE
            }
            show()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun validate(binding: CustomDialogAddCarBinding, isForHire: Boolean) : Boolean{
        var valid = true

        if (!isForHire){
            if (binding.carModelEditText.text.toString().isEmpty()) {
                binding.carModelEditText.error = context.getString(R.string.please_enter_model)
                valid = false
            }

            if (binding.regNumberEdit.text.toString().isEmpty()) {
                binding.regNumberEdit.error = context.getString(R.string.please_enter_registration_number)
                valid = false
            }
        }
        else{
            if (binding.minutesEditText.text.toString().isEmpty()) {
                binding.minutesEditText.error = context.getString(R.string.please_enter_registration_number)
                valid = false
            }
        }
        return valid
    }
}