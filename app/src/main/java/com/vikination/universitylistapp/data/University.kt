package com.vikination.universitylistapp.data

data class University(
    var name: String,
    var province: String,
    var webPage: String
){
    companion object{
        fun emptyUniversity() = University("","","")
    }
}