package ca.ulaval.ima.tp3

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.model.Model
import ca.ulaval.ima.tp3.networking.Tp3API
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewBrandModelAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityModels : AppCompatActivity() {

    var stateID = PlaceHolderClass(0, 0 , "")
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)
    private var modelList: List<Model> = emptyList()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapterCar: RecyclerViewBrandModelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_models)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val c = this
        getbrandModeleCar(c)

    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getbrandModeleCar( pContext : Context){
        tp3NetworkCenter.listModele().enqueue(object :
            Callback<Tp3API.ContentResponse<List<Model>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<Model>>>,
                response: Response<Tp3API.ContentResponse<List<Model>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {

                    modelList = it

                    adapterCar = RecyclerViewBrandModelAdapter(modelList)
                    mRecyclerView.adapter = adapterCar
                    adapterCar.setOnCountryClickListener {
                        Log.d("Test", it.name)

                        val returnIntent = intent
                        returnIntent.putExtra("SecondActivity", "from second Activity")
                        setResult(Activity.RESULT_OK, returnIntent)
                        Log.d("TAG", "onClick: Message sent")
                        finish()

                    }
                    for (car in it) {
                        supportActionBar?.title = "Car"
                        Log.d(
                            "Test",
                            "Got Car kilo ${car.name} with year ${car.brand.name}"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<Model>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }


}