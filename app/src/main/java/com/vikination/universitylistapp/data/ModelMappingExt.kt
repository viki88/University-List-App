package com.vikination.universitylistapp.data

import com.vikination.universitylistapp.data.source.local.LocalUniversity
import com.vikination.universitylistapp.data.source.network.NetworkUniversity


fun NetworkUniversity.toLocalUniversity() = LocalUniversity(
    name = name,
    stateProvince = province ?: "N/A",
    webPage = webPages[0]
)

fun LocalUniversity.toUniversity() = University(
    name = name,
    province = stateProvince,
    webPage = webPage
)

fun List<NetworkUniversity>.toLocalUniversityList() = map(NetworkUniversity::toLocalUniversity)

fun List<LocalUniversity>.toUniversityList() = map(LocalUniversity::toUniversity)

/**
 * sort list university by name
 * @return list university sorted by name
 */
fun List<University>.sortByName() = this.sortedWith( compareBy { it.name } )