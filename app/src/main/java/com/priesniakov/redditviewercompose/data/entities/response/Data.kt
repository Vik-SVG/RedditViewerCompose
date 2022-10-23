package com.priesniakov.redditviewercompose.data.entities.response

data class Data(
    val after: String,
    val before: String,
    val children: List<Children>,
    val dist: Int,
    val geo_filter: String,
    val modhash: String
)