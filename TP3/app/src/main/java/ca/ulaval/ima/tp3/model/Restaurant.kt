package ca.ulaval.ima.tp3.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("id") val id:Int,
    @SerializedName("name")  val name:String,
    @SerializedName("phone_number")  val phone_number:String
    )

