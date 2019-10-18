package com.example.dolang.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tour(
        @SerializedName("id") var id : Int? = null,
        @SerializedName("name") var name : String? = null,
        @SerializedName("category") var category : String? = null,
        @SerializedName("addrress") var address : String? = null,
        @SerializedName("price") var price : String? = null,
        @SerializedName("description") var description : String? = null,
        @SerializedName("read") var read : Int = 0,
        @SerializedName("image") var image : String? = null,
        @SerializedName("longitude") var longitude : String? = null,
        @SerializedName("latitude") var latitude : String? = null,
        @SerializedName("comment") var comment : List<ModelKomentar> = mutableListOf(),
        @SerializedName("panorama") var panorama : List<Panorama> = mutableListOf()
) : Parcelable

@Parcelize
data class Panorama(@SerializedName("path") var path : String? = null) : Parcelable