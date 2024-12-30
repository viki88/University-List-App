package com.vikination.universitylistapp.data.source.network

import com.google.gson.annotations.SerializedName

data class NetworkUniversity (
    var name: String,
    @SerializedName("state-province") var province: String?,
    @SerializedName("web_pages")var webPages: List<String>
)