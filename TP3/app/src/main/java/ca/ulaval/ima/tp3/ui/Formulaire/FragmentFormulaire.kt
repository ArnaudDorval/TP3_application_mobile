package ca.ulaval.ima.tp3.ui.Formulaire

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import ca.ulaval.ima.demo.demoretrofit.networking.NetworkCenter
import ca.ulaval.ima.tp3.MainActivityModels
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.model.OfferInput
import ca.ulaval.ima.tp3.model.OfferOutput
import ca.ulaval.ima.tp3.model.Token
import ca.ulaval.ima.tp3.model.UserInfo
import ca.ulaval.ima.tp3.networking.Tp3API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FragmentFormulaire: Fragment() {
    val tp3NetworkCenter = NetworkCenter.buildService(Tp3API::class.java)

    private lateinit var token :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_formulaire, container, false)
        val spinnerYear = root.findViewById<View>(R.id.spinnerAnnee) as Spinner
        val editTextKilo = root.findViewById<View>(R.id.editTextKilometrage) as EditText

        val spinnerTrans = root.findViewById<View>(R.id.spinnerTransmission) as Spinner
        val editPrice = root.findViewById<View>(R.id.editTextPrix) as EditText

        val checkBoxFromOwner = root.findViewById<View>(R.id.checkVenduProprio) as CheckBox
        val btnSoumettre = root.findViewById<Button>(R.id.button_Soumettre)

        val mChosenModel = root.findViewById<TextView>(R.id.textViewModele)

        // Spinner avec les annees
        val years = ArrayList<String>()
        val thisYear = Calendar.getInstance()[Calendar.YEAR]
        for (i in 1950..2022) {
            years.add(Integer.toString(i))
        }
        val yearAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        spinnerYear.setAdapter(yearAdapter)

        editTextKilo.setOnEditorActionListener { textView, actionId, keyEvent ->
            actionId == EditorInfo.IME_ACTION_DONE
        }

        editPrice.setOnEditorActionListener { textView, actionId, keyEvent ->
            actionId == EditorInfo.IME_ACTION_DONE
        }


        // Transmission
        val transmissions =
            arrayOf<String?>("Manuel", "Automatique", "Semi-Automatique")
        val transmissionAdapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, transmissions)
        spinnerTrans.adapter = transmissionAdapter


        // Liste des modeles
        mChosenModel.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, MainActivityModels::class.java)
            startActivityForResult(intent, 1)
        })


        btnSoumettre.setOnClickListener {
            var erreur = false
            val from_owner = checkBoxFromOwner.isChecked
            var kilometers = 0
            var price = 0
            val kilo = editTextKilo.text.toString()


            kilometers = kilo.toInt()

            if (kilometers < 0 || kilometers > 20000000) {
                erreur = true
            }

            val year = spinnerYear.selectedItem.toString().toInt()
            if (editPrice.text.toString().length > 8) {
                erreur = true
            } else {
                price = editPrice.text.toString().toInt()
                if (price < 0 || price > 9000000) {
                    erreur = true
                }
            }
            var transmission = spinnerTrans.selectedItem.toString()
            when (transmission) {
                "Automatique" -> transmission = "AT"
                "Manuel" -> transmission = "MA"
                "Semi-Automatique" -> transmission = "SM"
            }
            if (erreur) {
                Toast.makeText(requireContext(), "Formulaire incomplet", Toast.LENGTH_LONG).show()
            } else {

                createOffer(from_owner,kilometers,price,year,transmission, 1,  requireContext());
            }
        }


        return root
    }

    fun createOffer( from_owner : Boolean,  kilometers : Int, price : Int, year : Int, transmission : String, model : Int, pContext : Context){

        val input = OfferInput(from_owner,kilometers, year, price, transmission, model)

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

            if (pContext != null) {
                getToken(pEmail, pNum, input, pContext)
            }


        }

    }

    fun getToken(email:String, numID:Int, offerInput : OfferInput, pContext: Context) {
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

                        postOffer(offerInput, pContext)
                    }
                }

            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<Token>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }

    fun postOffer(offerInput : OfferInput, pContext: Context) {


        tp3NetworkCenter.postUserOffer("Basic " + token, offerInput).enqueue(object :
            Callback<Tp3API.ContentResponse<OfferOutput>> {
            override fun onResponse(
                call: Call<Tp3API.ContentResponse<OfferOutput>>,
                response: Response<Tp3API.ContentResponse<OfferOutput>>
            ) {
                Log.d("Test", response.body()?.meta.toString())
                Toast.makeText(pContext, "Post " + response.body()?.meta.toString(), Toast.LENGTH_LONG).show()
                if(response.isSuccessful){
                    response.body()?.content?.let {

                        Log.d("test-token", token)


                    }
                }

            }

            override fun onFailure(call: Call<Tp3API.ContentResponse<OfferOutput>>, t: Throwable) {
                Log.d("ima-demo", "getRestaurantDetail Failure $t")
            }

        })
    }


}

