package ca.ulaval.ima.tp3.model

import com.google.gson.annotations.SerializedName

data class modele(
    @SerializedName("id") val id:Int,
    @SerializedName("brand")  val brand: Brand,
    @SerializedName("name")  val name:String

    )

