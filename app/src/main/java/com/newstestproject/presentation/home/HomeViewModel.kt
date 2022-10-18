package com.newstestproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.core.util.Resource
import com.newstestproject.util.NewsSortType
import com.newstestproject.domain.use_case.GetTopArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopArticlesUseCase: GetTopArticlesUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadNews()
    }

    private var loadNewsJob: Job? = null

    fun loadNews() {
        _state.update { it.copy(isLoading = true, error = null, data = emptyList()) }
        loadNewsJob?.cancel()
        loadNewsJob = viewModelScope.launch {

            getTopArticlesUseCase().collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update { it.copy(
                            data = result.data,
                            error = null
                        ) }
                    }
                    is Resource.Error -> {
                        _state.update { it.copy(
                            error = result.message,
                            data = emptyList(),
                        ) }
                    }
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}