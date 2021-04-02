package ca.ulaval.ima.tp3
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class PlaceHolderClass (val brandID: Int?, val modelID: Int?, val recyclerTypeID: String?) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(brandID)
        parcel.writeValue(modelID)
        parcel.writeString(recyclerTypeID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceHolderClass> {
        override fun createFromParcel(parcel: Parcel): PlaceHolderClass {
            return PlaceHolderClass(parcel)
        }

        override fun newArray(size: Int): Array<PlaceHolderClass?> {
            return arrayOfNulls(size)
        }
    }

}

