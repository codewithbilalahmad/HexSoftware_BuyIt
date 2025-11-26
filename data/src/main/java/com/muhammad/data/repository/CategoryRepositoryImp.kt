package com.muhammad.data.repository

import com.muhammad.domain.model.CategoriesListModel
import com.muhammad.domain.network.NetworkService
import com.muhammad.domain.repository.CategoryRepository
import com.muhammad.domain.utils.Result

class CategoryRepositoryImp(
    private val networkService: NetworkService
) : CategoryRepository {
    override suspend fun getCategories(): Result<CategoriesListModel> {
        return networkService.getCategories()
    }
}