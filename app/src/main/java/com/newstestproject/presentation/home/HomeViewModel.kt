package com.newstestproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.core.util.Resource
import com.newstestproject.core.util.UiText
import com.newstestproject.domain.model.Article
import com.newstestproject.domain.use_case.GetCategoriesArticlesUseCase
import com.newstestproject.domain.use_case.GetSelectedCategoriesUseCase
import com.newstestproject.util.CategoryName
import com.newstestproject.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSelectedCategoriesUseCase: GetSelectedCategoriesUseCase,
    private val getCategoriesArticlesUseCase: GetCategoriesArticlesUseCase,
): ViewModel() {

    private val _loadErrors = MutableSharedFlow<UiText>()
    val loadErrors = _loadErrors.asSharedFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadNews()
    }

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _state.update { it.copy(query = query) }

        searchJob?.cancel()
        if(query.isBlank()) {
            loadNews()
            return
        }

        _state.update { it.copy(isLoading = true, error = null, data = emptyList()) }
        searchJob = viewModelScope.launch {
            delay(Constants.SEARCH_DELAY)
            val categories = getSelectedCategoriesUseCase()
            getCategoriesArticlesUseCase(categories, query).collect { result ->
                handleResourceResult(result)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private suspend fun handleResourceResult(result: Resource<List<Article>>) {
        when(result) {
            is Resource.Success -> {
                _state.update { it.copy(
                    data = result.data,
                    error = null
                ) }
            }
            is Resource.Error -> {
                _loadErrors.emit(result.message)
                if(state.value.data.isEmpty()) {
                    _state.update { it.copy(
                        error = result.message
                    ) }
                }
            }
            is Resource.Loading -> {
                _state.update { it.copy(
                    data = result.data.orEmpty(),
                ) }
            }
        }
    }

    private var filterJob: Job? = null
    fun onFilter(filter: CategoryName) {
        _state.update { it.copy(isLoading = true, error = null, data = emptyList()) }
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            getCategoriesArticlesUseCase(listOf(filter)).collect { result ->
                handleResourceResult(result)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onWidgetChanged(isSearchOpen: Boolean) {
        _state.update { it.copy(isSearchOpen = isSearchOpen) }
        onSearch("")
    }

    private var loadNewsJob: Job? = null

    fun loadNews() {
        _state.update { it.copy(isLoading = true, data = emptyList()) }
        loadNewsJob?.cancel()
        loadNewsJob = viewModelScope.launch {
            val categories = getSelectedCategoriesUseCase()

            _state.update { it.copy(categories = categories) }

            getCategoriesArticlesUseCase(categories).collect { result ->
                handleResourceResult(result)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}