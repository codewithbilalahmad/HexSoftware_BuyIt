package com.muhammad.buyit.presentation.screens.home.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.buyit.R
import com.muhammad.buyit.presentation.components.ProductItem
import com.muhammad.domain.model.Product

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeProductSection(
    modifier: Modifier = Modifier,
    isProductLoading: Boolean,
    productError: String?,
    icon: Int,
    @StringRes title: Int, onProductClick: (Product) -> Unit,
    products: List<Product>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        shape = MaterialShapes.Cookie12Sided.toShape(),
                        color = MaterialTheme.colorScheme.primary
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(icon),
                    contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        when {
            isProductLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }

            productError != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = Color.Transparent,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(text = productError, style = MaterialTheme.typography.bodyMedium)
                }
            }

            products.isNotEmpty() -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products, key = { it.id }) { product ->
                        ProductItem(
                            product = product,
                            onClick = onProductClick,
                            onAddProductToCart = {

                            })
                    }
                }
            }
        }
    }
}