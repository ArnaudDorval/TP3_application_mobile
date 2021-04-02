package ca.ulaval.ima.tp3.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.MainActivity4
import ca.ulaval.ima.tp3.PlaceHolderClass
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.model.OfferLightOutput
import ca.ulaval.ima.tp3.model.Token
import ca.ulaval.ima.tp3.model.UserInfo
import ca.ulaval.ima.tp3.networking.Tp3API
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewBrandAdapter
import ca.ulaval.ima.tp3.ui.ItemList.RecyclerViewCarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Fragment1 : Fragment() {

    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)

    private lateinit var token :String
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var adapterCar: RecyclerViewCarAdapter


    private var carList: List<OfferLightOutput> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_1, container, false)
        val c = this.context
        mRecyclerView = root.findViewById(R.id.recycler_view)
        mRecyclerView.layoutManager = LinearLayoutManager(c)

        val loginDialog = LayoutInflater.from(this.context).inflate(R.layout.alert_login, null)
        val alertBuilder = AlertDialog.Builder(this.context).setView(loginDialog)
        val myAlertDialog = alertBuilder.show()

        val buttonOk = loginDialog.findViewById<Button>(R.id.button_OK)
        val txtEmail = loginDialog.findViewById<EditText>(R.id.editTextEmail)
        val txtNumId = loginDialog.findViewById<EditText>(R.id.editTextNumId)

        buttonOk.setOnClickListener{
            myAlertDialog.dismiss()
            var pEmail = "arnaud.dorval-leblanc.1@ulaval.ca"
            var pNum = 111155275

            if(txtEmail.text != null){
                if(txtNumId.text != null){
                    pEmail = txtEmail.text.toString()
                    val t = txtNumId.text
                    pNum = t.toString().toInt()
                }
            }

            if (c != null) {
                getToken(pEmail, pNum, c)
            }


        }





        return root
    }


    fun getToken(email:String, numID:Int, pContext: Context) {
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

                        getbrandModeleCar(pContext)
                    }
                }

            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<Token>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }


    fun getbrandModeleCar(pContext : Context){
        tp3NetworkCenter.getMyOffer("Basic " + token).enqueue(object :
            Callback<Tp3API.ContentResponse<List<OfferLightOutput>>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>,
                response: Response<Tp3API.ContentResponse<List<OfferLightOutput>>>
            ) {
                Log.d("Test", response.body()?.meta.toString())
                if(response.isSuccessful){
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
                            Log.d(
                                "Test",
                                "Got Car kilo ${car.kilometers} with year ${car.year}, name ${car.model.name}"
                            )
                        }
                    }
                }else{
                    Toast.makeText(pContext, "No Announcement", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<List<OfferLightOutput>>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }
}