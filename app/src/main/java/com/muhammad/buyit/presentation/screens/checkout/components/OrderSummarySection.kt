package com.muhammad.buyit.presentation.screens.checkout.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.buyit.R
import com.muhammad.domain.model.SummaryData

@Composable
fun OrderSummarySection(modifier: Modifier = Modifier, orderSummary: SummaryData) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.order_summary),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            SummaryItem(label = R.string.quantity, value = "${orderSummary.items.size}")
            SummaryItem(label = R.string.subtotal, value = "$${orderSummary.subtotal.toInt()}")
            SummaryItem(label = R.string.discount, value = "$${orderSummary.discount.toInt()}")
            SummaryItem(
                label = R.string.delivery_charges,
                value = "$${orderSummary.shipping.toInt()}"
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.5.dp
            )
            SummaryItem(
                label = R.string.total_amount,
                value = "$${orderSummary.total.toInt()}"
            )
        }
    }
}

@Composable
private fun SummaryItem(@StringRes label: Int, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.surface)
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}