package ca.ulaval.ima.tp3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.model.Model
import ca.ulaval.ima.tp3.model.OfferLightOutput
import ca.ulaval.ima.tp3.networking.Tp3API
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewCarAdapter
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewModeleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    var stateID = PlaceHolderClass(0, 0 , "")
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)
    private var modeleList: List<Model> = emptyList()
    private var carList: List<OfferLightOutput> = emptyList()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapterModele: RecyclerViewModeleAdapter
    private lateinit var adapterCar: RecyclerViewCarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)



        //brandId = intent.getParcelableExtra<PlaceHolderClass>("pID")!!

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val c = this
        stateID = intent.getParcelableExtra<PlaceHolderClass>("pID")!!

        stateID.brandID?.let { getbrandModele(stateID.brandID!!, c) }
        supportActionBar?.title = "Modèle de voiture"




    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(item.itemId === android.R.id.home){
            val retData = Intent()
            setResult(RESULT_OK, retData);
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

    fun getbrandModele(brand_id:Int, pContext : Context){
        tp3NetworkCenter.listBrandModels(brand_id).enqueue(object :
            Callback<Tp3API.ContentResponse<List<Model>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<Model>>>,
                response: Response<Tp3API.ContentResponse<List<Model>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    modeleList = it

                    adapterModele = RecyclerViewModeleAdapter(modeleList)
                    mRecyclerView.adapter = adapterModele
                    adapterModele.setOnCountryClickListener {
                        Log.d("Test", it.name)

                        val ID = PlaceHolderClass(it.brand.id, it.id, "Car")
                        val intent = Intent (pContext, MainActivity3::class.java)
                        intent.putExtra("pID", ID);
                        startActivity(intent)

                    }
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


    fun getbrandModeleCar(brand_id:Int, modele_id:Int, pContext : Context){
        tp3NetworkCenter.listOfferCar(brand_id, modele_id).enqueue(object :
            Callback<Tp3API.ContentResponse<List<OfferLightOutput>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>,
                response: Response<Tp3API.ContentResponse<List<OfferLightOutput>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    carList = it

                    adapterCar = RecyclerViewCarAdapter(carList)
                    mRecyclerView.adapter = adapterCar
                    adapterCar.setOnCountryClickListener {
                        Log.d("Test", it.model.name)
                    }
                    for (car in it) {
                        Log.d(
                            "Test",
                            "Got Car kilo ${car.kilometers} with year ${car.year}, name ${car.model.name}"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }
}