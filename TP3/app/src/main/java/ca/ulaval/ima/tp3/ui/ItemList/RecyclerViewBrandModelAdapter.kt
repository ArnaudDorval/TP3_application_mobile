package ca.ulaval.ima.tp3.ui.ItemList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.ulaval.ima.tp3.R

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.model.Model


class RecyclerViewBrandModelAdapter(private val modeleList: List<Model>) : RecyclerView.Adapter<RecyclerViewBrandModelAdapter.ViewHolder>() {
    lateinit var onItemClickListener: ((Model) -> Unit)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        lateinit var view: View
        when (viewType) {
            SMALL_CELL_TYPE -> view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_item, viewGroup, false)
            LARGE_CELL_TYPE -> view = LayoutInflater.from(viewGroup.context).inflate(R.layout.fragment_item, viewGroup, false)
        }
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        val country = modeleList[position]
        return 1;
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val modele = modeleList[i]
        holder.mItem = modele
        holder.mModeleName.text = modele.brand.name + " " + modele.name

        holder.mView.setOnClickListener(){
            onItemClickListener.invoke(modele)
        }

    }

    override fun getItemCount(): Int {
        return modeleList.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mModeleName: TextView =  mView.findViewById(R.id.TextViewBrand_name)
        var mItem: Model? = null

    }

    fun setOnCountryClickListener(onItemClickListener: ((Model) -> Unit)) {
        this.onItemClickListener = onItemClickListener
    }



    companion object {
        private const val ASSETS_DIR = "images/"
        private const val SMALL_CELL_TYPE = 0
        private const val LARGE_CELL_TYPE = 1
    }
}