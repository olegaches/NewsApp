package com.newstestproject.core.util

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Loading<T>(val data: T? = null) : Resource<T>
    data class Error<T>(val message: UiText, val data: T? = null) : Resource<T>
}
