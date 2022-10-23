package com.priesniakov.redditviewercompose.data.entities.response

data class Image(
    val id: String,
    val resolutions: List<Resolution>,
    val source: Source,
)