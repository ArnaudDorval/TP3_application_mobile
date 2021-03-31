package ca.ulaval.ima.tp3.model

import ca.ulaval.ima.tp3.networking.Tp3API
import com.google.gson.annotations.SerializedName

data class PaginatedResultSerializer<T> (
    //@SerializedName("count") val totalCount: Int,
    //@SerializedName("next") val nextPage: Int,
    //@SerializedName("previous") val previousPage: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("meta") val meta: Tp3API.Meta
)