package com.vikination.universitylistapp.data

import com.vikination.universitylistapp.data.source.local.LocalUniversity
import com.vikination.universitylistapp.data.source.network.NetworkUniversity


fun NetworkUniversity.toUniversity() = University(
    name = name,
    province = province,
    webPage = webPages[0]
)

fun NetworkUniversity.toLocalUniversity() = LocalUniversity(
    name = name,
    stateProvince = province,
    webPage = webPages[0]
)

fun LocalUniversity.toUniversity() = University(
    name = name,
    province = stateProvince,
    webPage = webPage
)

fun List<NetworkUniversity>.toUniversityList() = map(NetworkUniversity::toUniversity)

fun List<NetworkUniversity>.toLocalUniversityList() = map(NetworkUniversity::toLocalUniversity)

fun List<LocalUniversity>.toUniversityList() = map(LocalUniversity::toUniversity)