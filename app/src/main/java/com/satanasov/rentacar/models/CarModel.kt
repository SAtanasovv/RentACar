package com.satanasov.rentacar.models

data class CarModel(
    val id                  : Long?   = 0,
    val carModel            : String? = "",
    val registrationNumber  : String? = "",
    var timeToBeRentedFor   : Long?   = 0,
    var currentTime         : Long?   = 0,
    var rentedTill          : Long?   = 0
)
