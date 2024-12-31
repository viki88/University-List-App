package com.vikination.universitylistapp.data

data class University(
    var name: String,
    var province: String,
    var webPage: String
){
    companion object{
        fun emptyUniversity() = University("","","")
        fun getDummyUniversity() = University("Univ 1","Jawa Barat","www.google.com")
        fun getDummyUniversities() = listOf(
            University("Univ 1","Jawa Barat","www.google.com"),
            University("Univ 2","Jawa Tengah","www.google.com"),
            University("Univ 3","Jawa Timur","www.google.com"),
            University("Univ 4","Jakarta","www.google.com"),
        )
    }
}