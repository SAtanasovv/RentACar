package com.satanasov.rentacar.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.satanasov.rentacar.R
import com.satanasov.rentacar.databinding.CustomDialogHireCarBinding

class HireCarCustomDialog(context: Context) : Dialog(context) {
    private var dialogListener  : HireCarDialogListener? = null

    interface HireCarDialogListener{
        fun onHireClicked(minutes: Long)
    }

    fun setListener(listener: HireCarDialogListener){
        dialogListener = listener
    }

    fun showDialog(){
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            val binding = CustomDialogHireCarBinding.inflate(layoutInflater)
            setContentView(binding.root)
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

            binding.addCarCancelButton.setOnClickListener { dismiss() }
            binding.addCarAddButton.setOnClickListener {
                if( validate(binding) ){
                    dialogListener?.onHireClicked(binding.minutesEditText.text.toString().toLong())
                    dismiss()
                }
            }
            show()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun validate(binding: CustomDialogHireCarBinding) : Boolean{
        var valid = true

            if (binding.minutesEditText.text.toString().isEmpty()) {
                binding.minutesEditText.error = context.getString(R.string.please_enter_time)
                valid = false
            }

            if (binding.minutesEditText.text.toString().isNotEmpty() && binding.minutesEditText.text.toString().toLong() == 0L) {
                binding.minutesEditText.error = context.getString(R.string.please_enter_values_greater)
                valid = false
            }
        return valid
    }
}