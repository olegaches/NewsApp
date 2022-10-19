package com.newstestproject.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.domain.use_case.AddCategoryUseCase
import com.newstestproject.domain.use_case.DeleteCategoryUseCase
import com.newstestproject.domain.use_case.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(categories = getAllCategoriesUseCase()) }
        }
    }

    fun onCategoryClicked(categoryIndex: Int) {
        viewModelScope.launch {
            val categories = state.value.categories
                .mapIndexed { index, category ->
                    if (index == categoryIndex) category.copy(
                        selected = !category.selected)
                    else
                        category
                }

            _state.update { it.copy(categories = categories) }
            val category = categories[categoryIndex]
            if(category.selected) {
                addCategoryUseCase(category)
            }
            else {
                deleteCategoryUseCase(category)
            }
        }
    }
}