package com.vikination.universitylistapp.data

import com.google.gson.annotations.SerializedName

data class University(
    var name: String,
    @SerializedName("state-province") var province: String,
    @SerializedName("web_pages")var webPages: List<String>
)