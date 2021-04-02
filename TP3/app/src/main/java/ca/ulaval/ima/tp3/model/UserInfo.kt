package ca.ulaval.ima.tp3.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("email")  val email:String,
    @SerializedName("identification_number")  val num:Int
    )

