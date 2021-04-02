package ca.ulaval.ima.tp3.model

import com.google.gson.annotations.SerializedName

data class OfferInput(
    @SerializedName("from_owner")  val from_owner:Boolean,
    @SerializedName("kilometers")  val kilometers:Int,
    @SerializedName("year")  val year:Int,
    @SerializedName("price")  val price:Int,
    @SerializedName("transmission")  val transmission:String,
    @SerializedName("model")  val model:Int
    )

