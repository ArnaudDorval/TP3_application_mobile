package ca.ulaval.ima.tp3.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.model.*
import ca.ulaval.ima.tp3.networking.Tp3API
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment2 : Fragment() {

    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)

    private lateinit var token :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_2, container, false)
        val buttonInternet = root.findViewById<Button>(R.id.button_test)

        val loginDialog = LayoutInflater.from(this.context).inflate(R.layout.alert_login, null)
        val alertBuilder = AlertDialog.Builder(this.context).setView(loginDialog)
        val myAlertDialog = alertBuilder.show()

        val buttonOk = loginDialog.findViewById<Button>(R.id.button_OK)

        buttonOk.setOnClickListener{
            myAlertDialog.dismiss()
            getToken("arnaud.dorval-leblanc.1@ulaval.ca", 111155275)
        }
        //getListOfBrands();
        buttonInternet.setOnClickListener(View.OnClickListener {
            getbrandModeleCar(11,145)
            //getListOfRestaurants();
            //Log.d("test", "fkn here");
            //getbrandModele(1)

        })

        return root

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

    fun getListOfRestaurants(){
        Log.d("test", "fkn here");
        tp3NetworkCenter.listRestaurants().enqueue(object :
            Callback<Tp3API.ContentResponse<PaginatedResultSerializer<Restaurant>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<PaginatedResultSerializer<Restaurant>>>,
                response: Response<Tp3API.ContentResponse<PaginatedResultSerializer<Restaurant>>>
            ) {
                response.body()?.content?.results?.let {
                   for (restaurant in it) {
                        Log.d(
                            "ima-demo",
                            "Got Restaurant ${restaurant.name} with id ${restaurant.id}"
                        )
                    }
                }
            }

            override fun onFailure(
                call: Call<Tp3API.ContentResponse<PaginatedResultSerializer<Restaurant>>>,
                t: Throwable
            ) {
                Log.d("ima-demo", "listRestaurants Failure $t")
            }

        }

        )
    }

    fun getbrandModele(brand_id:Int){
        tp3NetworkCenter.listBrandModels(brand_id).enqueue(object :
            Callback<Tp3API.ContentResponse<List<Model>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<Model>>>,
                response: Response<Tp3API.ContentResponse<List<Model>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    for (modele in it) {
                        Log.d(
                            "Test",
                            "Got Modele ${modele.name} with id ${modele.id}"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<Model>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }


    fun getbrandModeleCar(brand_id:Int, modele_id:Int){
        tp3NetworkCenter.listOfferCar(brand_id, modele_id).enqueue(object :
            Callback<Tp3API.ContentResponse<List<OfferLightOutput>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>,
                response: Response<Tp3API.ContentResponse<List<OfferLightOutput>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    for (car in it) {
                        Log.d(
                            "Test",
                            "Got Car kilo ${car.kilometers} with year ${car.year}"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }


    fun getToken(email:String, numID:Int) {
        val userInfo = UserInfo(email, numID)

        tp3NetworkCenter.postUserLogin(userInfo).enqueue(object :
            Callback<Tp3API.ContentResponse<Token>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<Token>>,
                response: Response<Tp3API.ContentResponse<Token>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                if(response.isSuccessful){
                    response.body()?.content?.let {

                            token = it.token
                        Log.d("test-token", token)
                    }
                }

            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<Token>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }
}