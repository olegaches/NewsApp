package com.newstestproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.core.util.Resource
import com.newstestproject.util.NewsSortType
import com.newstestproject.domain.use_case.GetAllNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetAllNewsUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadNews()
    }

    private var loadNewsJob: Job? = null

    fun loadNews() {
        _state.update { it.copy(isLoading = true) }
        loadNewsJob?.cancel()
        loadNewsJob = viewModelScope.launch {

            getNewsUseCase(
                from = LocalDate.of(2022, 9, 15),
                keyWord = "Apple",
                sortBy = NewsSortType.publishedAt
            ).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update { it.copy(
                            data = result.data
                        ) }
                    }
                    is Resource.Error -> {

                    }
                }
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}