package com.muhammad.data.model

import com.muhammad.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDataModel(
    val id: Int,
    val image: String,
    val title: String
) {
    fun toCategory() = Category(
        id = id,
        image = image,
        title = title
    )
}