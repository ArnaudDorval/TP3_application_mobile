package ca.ulaval.ima.tp3.networking

import ca.ulaval.ima.tp3.BuildConfig
import ca.ulaval.ima.tp3.model.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface Tp3API {

    companion object{
        const val API_V1 = "/api/v1/"
        const val BASE_URL: String = BuildConfig.BASE_URL
    }

    @GET(API_V1 + "brand/")
    fun listBrand(): Call<ContentResponse<PaginatedResultSerializer<Brand>>>

    @GET(API_V1 + "brand/{rest_id}/")
    fun listBrandModels(@Path("rest_id") restaurant_id: Int): Call<ContentResponse<Brand>>

    @GET(API_V1 + "model/")
    fun listModele(): Call<ContentResponse<PaginatedResultSerializer<modele>>>

    @GET(API_V1 + "offer/")
    fun listOffer(): Call<ContentResponse<PaginatedResultSerializer<offer>>>

    @GET(API_V1 + "offer/mine/")
    fun myOffer(): Call<ContentResponse<PaginatedResultSerializer<offer>>>

    @GET(API_V1 + "account/me/")
    fun myAccount(): Call<ContentResponse<PaginatedResultSerializer<Account>>>


    @Headers("Authorization: Bearer XXXXXX")
    @Multipart
    @POST(API_V1 + "offer/{review_id}/image/")
    fun postOfferPhoto(@Path("offer") offerId: Int,@Part image:MultipartBody.Part): Call<ContentResponse<offer>>

    data class ContentResponse<T> (
        @SerializedName("content") val content : T,
        @SerializedName("meta") val meta: Meta,
        @SerializedName("error") var error: Error
    )

    data class Error (
        @SerializedName("display") val displayMessage: String
    )

    data class Meta (
        @SerializedName("status_code") val statusCode: Int
    )

}