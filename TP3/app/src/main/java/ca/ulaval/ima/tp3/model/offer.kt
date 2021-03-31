package ca.ulaval.ima.tp3.model

import com.google.gson.annotations.SerializedName

data class offer(
    @SerializedName("id") val id:Int,
    @SerializedName("modele")  val modele: modele,
    @SerializedName("image")  val image:String,
    @SerializedName("description")  val description:String,
    @SerializedName("seller")  val seller:Account,
    @SerializedName("year")  val year:Int,
    @SerializedName("from_owner")  val from_owner:Boolean,
    @SerializedName("kilometers")  val kilometers:Int,
    @SerializedName("price")  val price:Int,
    @SerializedName("created")  val created:String,
    )

