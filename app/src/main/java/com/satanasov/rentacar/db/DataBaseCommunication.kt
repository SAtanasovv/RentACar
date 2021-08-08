package com.satanasov.rentacar.db

import android.content.Context
import com.satanasov.rentacar.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver

object DataBaseCommunication {
    private const val mDB_NAME     = "rentACar111.db"

    fun getDataBase(context: Context): Database {
        val driver = AndroidSqliteDriver(Database.Schema, context, mDB_NAME)
        return Database.invoke(driver)
    }
}