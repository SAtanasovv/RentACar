package com.satanasov.rentacar.presenter

import android.content.Context
import com.satanasov.rentacar.db.DataBaseQueries
import com.satanasov.rentacar.globalData.Utils.Companion.ADMIN
import com.satanasov.rentacar.globalData.Utils.Companion.PASSWORD
import com.satanasov.rentacar.globalData.Utils.Companion.USERNAME
import com.satanasov.rentacar.globalData.Utils.Companion.USER_ID
import com.satanasov.rentacar.globalData.Utils.Companion.USER_TYPE
import com.satanasov.rentacar.models.UserModel

class RegisterActivityPresenter(registerActivityView: RegisterActivityView) : BasePresenter<RegisterActivityView>() {

    private var dataBaseQueries         : DataBaseQueries = DataBaseQueries()
    private var context                 : Context

    init {
        attachView(registerActivityView)
        context = registerActivityView as Context
    }

    fun insertUser(userModel: UserModel){
        if (getUserByName(userModel.userName!!) == null){
            dataBaseQueries.insertUser(context, userModel)
            view?.goToLoginActivity()
        }
        else
            view?.showToast()
    }

    private fun getUserByName(username: String) : UserModel?{
        var userModel: UserModel? = null
        val userCursor = dataBaseQueries.getUserByUserName(context, username)

        while (userCursor.next()){
            userModel = UserModel(
                id          = userCursor.getLong(USER_ID),
                userName    = userCursor.getString(USERNAME),
                password    = userCursor.getString(PASSWORD),
                isAdmin     = userCursor.getString(USER_TYPE)?.equals(ADMIN, ignoreCase = true)!!
            )
        }
        return userModel
    }
}

interface RegisterActivityView{
    fun goToLoginActivity()
    fun showToast()
}