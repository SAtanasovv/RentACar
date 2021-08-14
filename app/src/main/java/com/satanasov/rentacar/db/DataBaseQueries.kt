package com.satanasov.rentacar.db

import android.content.Context
import android.widget.Toast
import com.satanasov.rentacar.R
import com.satanasov.rentacar.globalData.Utils
import com.satanasov.rentacar.models.CarModel
import com.satanasov.rentacar.models.UserModel
import com.squareup.sqldelight.db.SqlCursor

class DataBaseQueries {
    fun insertCar(context: Context, carModel: CarModel){
        val database = DataBaseCommunication.getDataBase(context)
        database.carQueries.transaction {
            afterCommit {  }
            afterRollback { Toast.makeText(context, R.string.insert_failed, Toast.LENGTH_SHORT).show() }

            database.carQueries.InsertCarModel(carModel.carModel, carModel.registrationNumber)
        }
    }

    fun getCarList(context: Context) : SqlCursor = DataBaseCommunication.getDataBase(context).carQueries.GetAllCars().execute()

    fun deleteCar(context: Context, id: Long){
        val database = DataBaseCommunication.getDataBase(context)
        database.carQueries.transaction {
            afterCommit {  }
            afterRollback { Toast.makeText(context, R.string.delete_failed, Toast.LENGTH_SHORT).show() }

            database.carQueries.DeleteCarModel(id)
        }
    }

    fun updateCar(context: Context, carModel: CarModel) {
        val database = DataBaseCommunication.getDataBase(context)
        database.carQueries.transaction {
            afterCommit { }
            afterRollback { Toast.makeText(context, R.string.update_failed, Toast.LENGTH_SHORT).show() }

            database.carQueries.UpdateCarModel(carModel.carModel, carModel.registrationNumber, carModel.timeToBeRentedFor.toString(), carModel.currentTime.toString(), carModel.rentedTill.toString(), carModel.id!!)
        }
    }

    fun insertUser(context: Context, userModel: UserModel){
        val database = DataBaseCommunication.getDataBase(context)
        database.userQueries.transaction {
            afterCommit {  }
            afterRollback { Toast.makeText(context, R.string.insert_failed, Toast.LENGTH_SHORT).show() }

            database.userQueries.InsertUser(userModel.userName, userModel.password, if (userModel.isAdmin) Utils.ADMIN else Utils.USER)
        }
    }

    fun getAllUsers(context: Context) : SqlCursor = DataBaseCommunication.getDataBase(context).userQueries.GetAllUsers().execute()

    fun getUserByUserName(context: Context, username: String): SqlCursor = DataBaseCommunication.getDataBase(context).userQueries.GetUserByUserName(username).execute()

    fun getUserByUserNameAndPassword(context: Context, username: String, password: String): SqlCursor = DataBaseCommunication.getDataBase(context).userQueries.GetUserByUserNameAndPassword(username, password).execute()
}