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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.model.*
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class RecyclerViewCarAdapter(private val carList: List<OfferLightOutput>) : RecyclerView.Adapter<RecyclerViewCarAdapter.ViewHolder>() {
    lateinit var onItemClickListener: ((OfferLightOutput) -> Unit)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        lateinit var view: View
        when (viewType) {
            SMALL_CELL_TYPE -> view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_offre, viewGroup, false)
            LARGE_CELL_TYPE -> view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_offre, viewGroup, false)
        }
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        val car = carList[position]
        return 1;
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val car = carList[i]
        holder.mItem = car
        if(car.model != null){
            holder.mCarName.text = car.model.brand.name + " " +car.model.name
            holder.mCarAnnee.text = car.year.toString()
            holder.mCarKilo.text = car.kilometers.toString() + " Km"
            holder.mCarPrice.text = car.price.toString() + " $"

            Picasso.get().load(car.image).into(holder.mCarImageView)

        }else {
            holder.mCarName.text = "test"
        }


        holder.mView.setOnClickListener(){
            onItemClickListener.invoke(car)
        }

    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mCarName: TextView =  mView.findViewById(R.id.textViewModele)
        val mCarAnnee: TextView = mView.findViewById(R.id.textViewAnnee)
        val mCarKilo: TextView = mView.findViewById(R.id.textViewKilo)
        val mCarPrice: TextView = mView.findViewById(R.id.textViewPrice)
        val mCarImageView: ImageView = mView.findViewById(R.id.imageViewVoiture)
        var mItem: OfferLightOutput? = null

    }

    fun setOnCountryClickListener(onItemClickListener: ((OfferLightOutput) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }



    companion object {
        private const val ASSETS_DIR = "images/"
        private const val SMALL_CELL_TYPE = 0
        private const val LARGE_CELL_TYPE = 1
    }
}