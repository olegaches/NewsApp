package com.newstestproject.presentation.category_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newstestproject.domain.model.Category
import com.newstestproject.domain.use_case.AddCategoryUseCase
import com.newstestproject.domain.use_case.DeleteCategoryUseCase
import com.newstestproject.domain.use_case.GetAllCategoriesUseCase
import com.newstestproject.domain.use_case.GetUserCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySearchViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow(CategorySearchState())
    val state = _state.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            val allCategories = getAllCategoriesUseCase()
            val selectedCategories = getUserCategoriesUseCase()
            val categoryList = allCategories.map {
                if(selectedCategories.contains(it)) {
                    Category(it, true)
                }
                else {
                    Category(it)
                }
            }
            _state.update { it.copy(categories = categoryList) }
        }
    }

    fun onSearch(query: String) {
        _state.update { it.copy(query = query) }
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