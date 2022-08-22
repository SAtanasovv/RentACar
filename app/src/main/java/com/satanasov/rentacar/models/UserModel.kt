package com.satanasov.rentacar.models
/*TEST*/
data class UserModel(
    val id          : Long?   = 0,
    val userName    : String? = "",
    val password    : String? = "",
    var isAdmin     : Boolean = false
)