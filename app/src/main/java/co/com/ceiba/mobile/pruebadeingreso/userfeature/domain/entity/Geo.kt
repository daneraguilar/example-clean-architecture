package co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geo(
    val lat: String,
    val lng: String
) : Parcelable
