package ca.ulaval.ima.tp3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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


class MainActivity3 : AppCompatActivity() {

    var stateID = PlaceHolderClass(0, 0 , "")
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)
    private var carList: List<OfferLightOutput> = emptyList()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapterCar: RecyclerViewCarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        //setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val c = this
        stateID = intent.getParcelableExtra<PlaceHolderClass>("pID")!!

        stateID.brandID?.let { getbrandModeleCar(stateID.brandID!!,stateID.modelID!!, c) }

    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(item.itemId === android.R.id.home){
            val retData = Intent()
            setResult(RESULT_OK, retData);
            finish()

        }
        return super.onOptionsItemSelected(item)
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

                        val ID = PlaceHolderClass(it.id, it.id, "fullDescription")
                        val intent = Intent (pContext, MainActivity4::class.java)
                        intent.putExtra("pID", ID);
                        startActivity(intent)

                    }
                    for (car in it) {
                        supportActionBar?.title = car.model.brand.name + " " + car.model.name
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