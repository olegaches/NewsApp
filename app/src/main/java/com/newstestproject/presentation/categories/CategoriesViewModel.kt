package com.newstestproject.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.domain.model.Category
import com.newstestproject.domain.use_case.AddCategoryUseCase
import com.newstestproject.domain.use_case.DeleteCategoryUseCase
import com.newstestproject.domain.use_case.GetAllCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getAllCategoriesUseCase().collectLatest { result ->
                _state.update { it.copy(categories = result) }
            }
        }
    }

    fun onCategoryClicked(category: Category) {
        viewModelScope.launch {
            if(category.selected) {
                addCategoryUseCase(category)
            }
            else {
                deleteCategoryUseCase(category)
            }
        }
    }
}