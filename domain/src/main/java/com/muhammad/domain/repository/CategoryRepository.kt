package com.muhammad.domain.repository

import com.muhammad.domain.model.CategoriesListModel
import com.muhammad.domain.utils.Result

interface CategoryRepository {
    suspend fun getCategories(): Result<CategoriesListModel>
}