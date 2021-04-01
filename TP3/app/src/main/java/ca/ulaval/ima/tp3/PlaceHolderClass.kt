package ca.ulaval.ima.tp3
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class PlaceHolderClass (val ID: Int?) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(ID)
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