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
        const val BASE_URL: String = "https://tp3.infomobile.app"
        //const val BASE_URL: String = "https://kungry.ca"
    }
    ///https://tp3.infomobile.app/api/v1/brand/

    @GET(API_V1 + "restaurant/")
    fun listRestaurants(): Call<ContentResponse<PaginatedResultSerializer<Restaurant>>>

    @GET(API_V1 + "brand/")
    fun listBrand(): Call<ContentResponse<List<Brand>>>

    @GET(API_V1 + "brand/{rest_id}/models")
    fun listBrandModels(@Path("rest_id") restaurant_id: Int): Call<ContentResponse<List<Model>>>

    @GET(API_V1 + "model/")
    fun listModele(): Call<ContentResponse<List<Model>>>

    @GET(API_V1 + "offer/")
    fun listOffer(): Call<ContentResponse<List<offer>>>

    @GET(API_V1 + "offer/{rest_id}/details/")
    fun ModelDescription(@Path("rest_id") restaurant_id: Int): Call<ContentResponse<OfferOutput>>

    @GET(API_V1 + "offer/search/")
    fun listOfferCar(@Query("model")modelId :Int, @Query("brand") brandId: Int): Call<ContentResponse<List<OfferLightOutput>>>


    @GET(API_V1 + "offer/mine/")
    fun myOffer(): Call<ContentResponse<List<offer>>>

    @GET(API_V1 + "account/me/")
    fun myAccount(): Call<ContentResponse<List<Account>>>


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