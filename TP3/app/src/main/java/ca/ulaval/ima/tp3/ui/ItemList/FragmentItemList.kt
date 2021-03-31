package ca.ulaval.ima.tp3.ui.ItemList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.model.Brand
import ca.ulaval.ima.tp3.model.PaginatedResultSerializer
import ca.ulaval.ima.tp3.networking.Tp3API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File


class FragmentItemList : Fragment() {

    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_list, container, false)
        //getListOfBrands();
    }

    fun getListOfBrands(){
        tp3NetworkCenter.listBrand().enqueue(object :
            Callback<Tp3API.ContentResponse<List<Brand>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<Brand>>>,
                response: Response<Tp3API.ContentResponse<List<Brand>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    for (brand in it) {
                        Log.d(
                            "Test",
                            "Got Brand ${brand.name} with id ${brand.id}"
                        )
                    }
                }
            }

            override fun onFailure(
                call: Call<Tp3API.ContentResponse<List<Brand>>>,
                t: Throwable
            ) {
                Log.d("Test", "listBrands Failure $t")
            }

        }

        )
    }
}