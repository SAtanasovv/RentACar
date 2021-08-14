package com.satanasov.rentacar.presenter

import android.content.Context
import com.satanasov.rentacar.db.DataBaseQueries
import com.satanasov.rentacar.globalData.Utils.Companion.ADMIN
import com.satanasov.rentacar.globalData.Utils.Companion.PASSWORD
import com.satanasov.rentacar.globalData.Utils.Companion.USERNAME
import com.satanasov.rentacar.globalData.Utils.Companion.USER_ID
import com.satanasov.rentacar.globalData.Utils.Companion.USER_TYPE
import com.satanasov.rentacar.models.UserModel

class LoginActivityPresenter(loginActivityView: LoginActivityView) : BasePresenter<LoginActivityView>() {
    var         userModelList   : ArrayList<UserModel>  = ArrayList()
    private var dataBaseQueries : DataBaseQueries       = DataBaseQueries()
    private var context         : Context

    init {
        attachView(loginActivityView)
        context = loginActivityView as Context
    }

    override fun subscribe() {
        super.subscribe()
        userModelList = getUserList()
    }

    fun onLoginClicked(username: String, password: String){
        val userModel = getUserByNameAndPassword(username, password)
        if (userModel != null){
            view?.goToMainActivity(userModel.isAdmin)
            return
        }
        view?.showErrorToast()
    }

    private fun getUserList() : ArrayList<UserModel>{
        var userModel: UserModel
        val userCursor = dataBaseQueries.getAllUsers(context)

        userModelList = arrayListOf()

        while (userCursor.next()){
            userModel = UserModel(
                id          = userCursor.getLong(USER_ID),
                userName    = userCursor.getString(USERNAME),
                password    = userCursor.getString(PASSWORD),
                isAdmin     = userCursor.getString(USER_TYPE)?.equals(ADMIN, ignoreCase = true)!!
            )
            userModelList.add(userModel)
        }
        checkListIsEmpty()
        return userModelList
    }

    private fun checkListIsEmpty(){
        if (userModelList.isNotEmpty())
            view?.noUsersFound(false)
        else
            view?.noUsersFound(true)
    }

    private fun getUserByNameAndPassword(username: String, password: String) : UserModel?{
        var userModel: UserModel? = null
        val userCursor = dataBaseQueries.getUserByUserNameAndPassword(context, username, password)

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
interface LoginActivityView {
    fun noUsersFound(show: Boolean)
    fun goToMainActivity(isAdmin: Boolean)
    fun showErrorToast()
}