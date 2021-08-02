package com.satanasov.rentacar.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.satanasov.rentacar.R
import com.satanasov.rentacar.models.CarModel
import kotlinx.android.synthetic.main.custom_dialog_add_car.*

class AddCarCustomDialog(context: Context) : Dialog(context) {
    private var dialogListener  : AddCarDialogListener? = null

    interface AddCarDialogListener{
        fun onCancelClicked()
        fun onAddClicked(carModel: CarModel)
    }

    fun setListener(listener: AddCarDialogListener){
        dialogListener = listener
    }

    fun showDialog(){
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.custom_dialog_add_car)
            window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

            addCarCancelButton.setOnClickListener {
                dialogListener?.onCancelClicked()
                dismiss()
            }

            addCarAddButton.setOnClickListener {
                if( validate() ){
                    dialogListener?.onAddClicked(CarModel(carModelEditText.text.toString(),regNumberEdit.text.toString()))
                    dismiss()
                }
            }
            show()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun validate() : Boolean{
        var valid = true

        if (carModelEditText.text.toString().isEmpty()) {
            carModelEditText.error = context.getString(R.string.please_enter_model)
            valid = false
        }

        if (regNumberEdit.text.toString().isEmpty()) {
            regNumberEdit.error = context.getString(R.string.please_enter_registration_number)
            valid = false
        }

        return valid
    }
}