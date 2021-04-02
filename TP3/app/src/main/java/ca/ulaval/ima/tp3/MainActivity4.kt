package ca.ulaval.ima.tp3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.model.OfferOutput
import ca.ulaval.ima.tp3.networking.Tp3API
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity4 : AppCompatActivity() {

    var stateID = PlaceHolderClass(0, 0 , "")
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val c = this
        stateID = intent.getParcelableExtra<PlaceHolderClass>("pID")!!

        stateID.brandID?.let { getDescriptionModel(stateID.brandID!!, c) }
        supportActionBar?.title = "Vendre une voiture"
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
        if(item.itemId === android.R.id.home){
            val retData = Intent()
            setResult(RESULT_OK, retData);
            finish()

        }
        return super.onOptionsItemSelected(item)
    }

    fun getDescriptionModel(offer_id:Int, pContext : Context){
        tp3NetworkCenter.ModelDescription(offer_id).enqueue(object :
            Callback<Tp3API.ContentResponse<OfferOutput>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<OfferOutput>>,
                response: Response<Tp3API.ContentResponse<OfferOutput>>
            ) {
                Log.d("Test", response.body()?.meta.toString())

                response.body()?.content?.let {

                    val mCarBrand: TextView =  findViewById(R.id.textViewBrand)
                    val mCarModel: TextView =  findViewById(R.id.textViewModel)
                    val mCarAnnee: TextView = findViewById(R.id.textViewYear)
                    val mCarKilo: TextView = findViewById(R.id.textViewKilometrage)
                    val mCarPrice: TextView = findViewById(R.id.textViewPrix)
                    val mCarTransmission: TextView = findViewById(R.id.textViewTrans)
                    val mCarImageView: ImageView = findViewById(R.id.imageViewToto)

                    val mCarNom: TextView = findViewById(R.id.textViewName)
                    val mCarCourriel: TextView = findViewById(R.id.textViewCourriel)
                    val mCarProprio: TextView = findViewById(R.id.textViewProprio)


                    mCarBrand.text = it.model.brand.name + " " +it.model.name
                    mCarModel.text = it.model.name
                    mCarAnnee.text = it.year.toString()
                    mCarKilo.text = it.kilometers.toString() + " Km"
                    mCarPrice.text = it.price.toString() + " $"
                    mCarTransmission.text = it.transmission

                    mCarNom.text = it.seller.first_name + " " + it.seller.last_name
                    mCarCourriel.text = it.seller.email
                    mCarProprio.text = it.from_owner.toString()

                    Picasso.get().load(it.image).into(mCarImageView)


                    Log.d(
                        "Test",
                        "Got Car kilo ${it.id} with year ${it.year}, name ${it.model.name}"
                    )

                }
            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<OfferOutput>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }
}