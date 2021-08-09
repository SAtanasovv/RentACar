package com.satanasov.rentacar.presenter

import android.content.Context
import com.satanasov.rentacar.db.DataBaseQueries
import com.satanasov.rentacar.models.CarModel
import kotlin.collections.ArrayList

class MainActivityPresenter(mainActivityView: MainActivityView) : BasePresenter<MainActivityView>() {
    var currentCarToHire                : CarModel              = CarModel()

    private var carModelList            : ArrayList<CarModel>   = ArrayList()
    private var dataBaseQueries         : DataBaseQueries       = DataBaseQueries()
    private var mainActivityViewContext : MainActivityView

    init {
        attachView(mainActivityView)
        this.mainActivityViewContext = mainActivityView
    }

    override fun subscribe() {
        super.subscribe()
        carModelList = getCarList()

        if (carModelList.isNotEmpty())
            view?.updateList(carModelList)
    }

    fun insertCar(carModel: CarModel){
        dataBaseQueries.insertCar(mainActivityViewContext as Context, carModel)
        view?.updateList(getCarList())
    }

    private fun getCarList() : ArrayList<CarModel>{
        var carModel: CarModel
        val carCursor   = dataBaseQueries.getCarList(mainActivityViewContext as Context)

        carModelList    = arrayListOf()

        while (carCursor.next()){
            carModel = CarModel(
                id                  = carCursor.getLong(CAR_ID),
                carModel            = carCursor.getString(CAR_MODEL),
                registrationNumber  = carCursor.getString(CAR_REGISTRATION_NUMBER),
                timeToBeRentedFor   = carCursor.getLong(CAR_TIME_TO_BE_RENTED_FOR),
                currentTime         = carCursor.getLong(CAR_CURRENT_TIME),
                rentedTill          = carCursor.getLong(CAR_RENTED_TILL)
            )
            carModelList.add(carModel)
        }
        checkListIsEmpty()
        return carModelList
    }

    fun hireCar(carModel: CarModel){
        carModel.rentedTill = carModel.currentTime?.plus(carModel.timeToBeRentedFor!! * MILLISECONDS)
        dataBaseQueries.updateCar(mainActivityViewContext as Context, carModel)
        view?.updateList(getCarList())
    }

    fun deleteCar(adapterPosition: Int){
        dataBaseQueries.deleteCar(mainActivityViewContext as Context, carModelList[adapterPosition].id!!)
        carModelList = getCarList()
        view?.deleteCarModel(adapterPosition)
    }

    private fun checkListIsEmpty(){
        if (carModelList.isNotEmpty())
            view?.setNoCarsAddedText(false)
        else
            view?.setNoCarsAddedText(true)
    }

    companion object{
        const val CAR_ID                        = 0
        const val CAR_MODEL                     = 1
        const val CAR_REGISTRATION_NUMBER       = 2
        const val CAR_TIME_TO_BE_RENTED_FOR     = 3
        const val CAR_CURRENT_TIME              = 4
        const val CAR_RENTED_TILL               = 5

        const val MILLISECONDS                  = 60000
    }
}
interface MainActivityView {
    fun setAdapter(carModelList: ArrayList<CarModel>)
    fun updateList(carModelList: ArrayList<CarModel>)
    fun deleteCarModel(adapterPosition: Int)
    fun setNoCarsAddedText(show: Boolean)
}