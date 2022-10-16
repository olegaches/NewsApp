package com.newstestproject.data.remote.dto

data class ErrorDto(
    val status: String,
    val code: String,
    val message: String,
)