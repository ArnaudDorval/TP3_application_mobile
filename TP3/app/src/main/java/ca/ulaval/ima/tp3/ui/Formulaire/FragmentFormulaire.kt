package ca.ulaval.ima.tp3.ui.Formulaire

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.fragment.app.Fragment
import ca.ulaval.ima.tp3.MainActivityModels
import ca.ulaval.ima.tp3.R
import java.util.*


class FragmentFormulaire: Fragment() {

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
        for (i in 1950..thisYear) {
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

            if (kilometers < 0 || kilometers > 600000) {
                erreur = true
            }

            val year = spinnerYear.selectedItem.toString().toInt()
            if (editPrice.text.toString().length > 8) {
                erreur = true
            } else {
                price = editPrice.text.toString().toInt()
                if (price < 0 || price > 1000000) {
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

            }
        }


        return root
    }


}