package com.newstestproject.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(

) : ViewModel() {

    private val _route = MutableStateFlow(Screen.HomeScreen.route)
    val route = _route.asStateFlow()

    fun onRouteChanged(route: String) {
        _route.value = route
    }
}