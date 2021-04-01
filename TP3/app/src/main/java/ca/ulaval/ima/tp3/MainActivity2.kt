package ca.ulaval.ima.tp3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.model.modele
import ca.ulaval.ima.tp3.networking.Tp3API
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewModeleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    var brandId = PlaceHolderClass(0)
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)
    private var modeleList: List<modele> = emptyList()

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewModeleAdapter

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
        getbrandModele(1, c)
        //brandId.ID?.let {  }

        //mRecyclerView.adapter = adapter

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
            Callback<Tp3API.ContentResponse<List<modele>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<modele>>>,
                response: Response<Tp3API.ContentResponse<List<modele>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {
                    modeleList = it

                    adapter = RecyclerViewModeleAdapter(modeleList)
                    mRecyclerView.adapter = adapter
                    adapter.setOnCountryClickListener {
                        Log.d("Test", it.name)
                    }
                    for (modele in it) {
                        Log.d(
                            "Test",
                            "Got Modele ${modele.name} with id ${modele.id}"
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<modele>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }
}