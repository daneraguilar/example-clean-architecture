package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    @SerializedName("zipcode") val zipCode: String,
    val geo: Geo
) : Parcelable